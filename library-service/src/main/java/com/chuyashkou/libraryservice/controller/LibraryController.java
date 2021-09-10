package com.chuyashkou.libraryservice.controller;

import com.chuyashkou.libraryservice.model.*;
import com.chuyashkou.libraryservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class LibraryController {

    @Autowired
    AccountingService accountingService = new AccountingService();

    @Autowired
    UserService userService = new UserService();

    @Autowired
    BookService bookService = new BookService();

    @Autowired
    PublishingHouseService publishingHouseService = new PublishingHouseService();

    @Autowired
    ClientService clientService = new ClientService();

    @Autowired
    AuthorService authorService = new AuthorService();


    private User user;


    //User controller
    @GetMapping("/logout")
    public String logout(Model model) {
        this.user = null;
        User user = new User();
        model.addAttribute("user", user);
        return "singIn";
    }

    @GetMapping("/")
    public String singIn(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "singIn";
    }

    @GetMapping("/singUp")
    public String singUp(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", this.user);
        modelAndView.setViewName("home");
        return modelAndView;
    }


    @PostMapping("/home")
    public ModelAndView singIn(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        user = userService.getUser(user);
        if (user != null) {
            this.user = user;
            modelAndView.addObject("user", user);
            modelAndView.setViewName("home");
            return modelAndView;
        }
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        if (userService.addUser(user)) {
            return "singIn";
        }
        return "register";
    }


    //Book Controller
    @PostMapping("/searchBookByPublishingHouse/result")
    public ModelAndView searchBooksByPublishingHouseResult(@ModelAttribute("publishingHouse") PublishingHouse publishingHouse) {
        ModelAndView modelAndView = new ModelAndView();
        PublishingHouse publishingHouse1 = publishingHouseService.getPublishingHouse(publishingHouse);
        if (publishingHouse1 == null) {
            modelAndView.setViewName("getAllSearchBooksByPublishingHouse");
            return modelAndView;
        }
        List<Book> books = bookService.getBooksByPublishingHouseId(publishingHouse1.getId());
        for (int i = 0; i < books.size(); i++) {
            books.set(i, bookCompleted(books.get(i)));
        }
        modelAndView.addObject("books", books);
        modelAndView.setViewName("getAllSearchBooksByPublishingHouse");
        return modelAndView;
    }

    @GetMapping("/searchBooksByPublishingHouse")
    public ModelAndView searchBooksByPublishingHouse(ModelAndView modelAndView) {
        PublishingHouse publishingHouse = new PublishingHouse();
        modelAndView.addObject("publishingHouse", publishingHouse);
        modelAndView.setViewName("searchBookByPublishingHouse");
        return modelAndView;
    }


    @PostMapping("/searchBookByAuthor/result")
    public ModelAndView searchBooksByAuthorResult(@ModelAttribute("author") Author author) {
        ModelAndView modelAndView = new ModelAndView();
        Author author1 = authorService.getAuthor(author);
        if (author1 == null) {
            modelAndView.setViewName("getAllSearchBooksByAuthor");
            return modelAndView;
        }
        List<Book> books = bookService.getBooksByAuthorId(author1.getId());
        for (int i = 0; i < books.size(); i++) {
            books.set(i, bookCompleted(books.get(i)));
        }
        modelAndView.addObject("books", books);
        modelAndView.setViewName("getAllSearchBooksByAuthor");
        return modelAndView;
    }


    @GetMapping("/searchBooksByAuthor")
    public ModelAndView searchBooksByAuthor(ModelAndView modelAndView) {
        Author author = new Author();
        modelAndView.addObject("author", author);
        modelAndView.setViewName("searchBookByAuthor");
        return modelAndView;
    }


    @GetMapping("/getAllBooks/returnBook/{id}")
    public String returnBook(@PathVariable(name = "id") Long id) {
        Book book = new Book();
        book.setId(id);
        Book book1 = bookService.getBookById(id);
        if (book1.getClientId() != 1L) {
            accountingService.addNewAccounting(currentDate(), "returned", book1.getId(), book1.getClientId(), this.user.getId());
        }
        bookService.updateBookByClient(book, 1L);
        bookService.updateBookByUser(book, 1L);
        return "redirect:/getAllBooks";
    }

    @PostMapping("/getAllBooks/checkOutBookAClient")
    public String checkOutBookAClient(@ModelAttribute("book") Book book) {
        bookService.updateBookByUser(book, userService.getUser(this.user).getId());
        Client client = clientService.getClient(book.getClient());
        if (client != null) {
            bookService.updateBookByClient(book, client.getId());
            accountingService.addNewAccounting(currentDate(), "issued", book.getId(), client.getId(), this.user.getId());
        } else {
            clientService.addClient(book.getClient());
            bookService.updateBookByClient(book, clientService.getClient(book.getClient()).getId());
            accountingService.addNewAccounting(currentDate(), "issued", book.getId(), clientService.getClient(book.getClient()).getId(), this.user.getId());
        }
        return "redirect:/getAllBooks";
    }


    @GetMapping("/getAllBooks/checkOut/{id}")
    public ModelAndView checkOutBook(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("checkOutABook");
        Book book = bookService.getBookById(id);
        Book bookCompleted = bookCompleted(book);
        modelAndView.addObject("book", bookCompleted);
        return modelAndView;
    }

    @GetMapping("/getAllBooks/update/{id}")
    public ModelAndView showUpdateBookPage(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("updateBook");
        Book book = bookService.getBookById(id);
        Book bookCompleted = bookCompleted(book);
        modelAndView.addObject("book", bookCompleted);
        return modelAndView;
    }

    @PostMapping("/getAllBooks/updateBook")
    public String updateBook(@ModelAttribute("book") Book book) {
        if (bookService.getBook(book) == null) {
            bookService.updateTitleById(book);
        }
        Author author = authorService.getAuthor(book.getAuthor());
        PublishingHouse publishingHouse = publishingHouseService.getPublishingHouse(book.getPublishingHouse());

        if (author != null) {
            bookService.updateBookByAuthor(book, author.getId());
        } else {
            authorService.addAuthor(book.getAuthor());
            bookService.updateBookByAuthor(book, authorService.getAuthor(book.getAuthor()).getId());
        }
        if (publishingHouse != null) {
            bookService.updateBookByPublishingHouse(book, publishingHouse.getId());
        } else {
            publishingHouseService.addPublishingHouse(book.getPublishingHouse());
            bookService.updateBookByPublishingHouse(book, publishingHouseService
                    .getPublishingHouse(book.getPublishingHouse()).getId());
        }

        return "redirect:/getAllBooks";
    }


    @GetMapping("/getAllBooks/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") Long id) {
        bookService.deleteBookById(id);
        return "redirect:/getAllBooks";
    }

    @GetMapping("/getAllBooks")
    public ModelAndView getAllBooks(ModelAndView modelAndView) {
        List<Book> allBook = bookService.getAllBook();
        for (int i = 0; i < allBook.size(); i++) {
            allBook.set(i, bookCompleted(allBook.get(i)));
        }
        modelAndView.addObject("books", allBook);
        modelAndView.setViewName("getAllBooks");
        return modelAndView;
    }

    @GetMapping("/addBook")
    public ModelAndView addBookHome(ModelAndView modelAndView) {
        modelAndView.addObject("book", new Book());
        modelAndView.setViewName("addBook");
        return modelAndView;
    }

    @PostMapping("/addBookComplete")
    public ModelAndView addBook(@ModelAttribute("book") Book book) {
        ModelAndView modelAndView = new ModelAndView();
        book.setUser(defaultUser());
        book.setClient(defaultClient());
        publishingHouseService.addPublishingHouse(book.getPublishingHouse());
        authorService.addAuthor(book.getAuthor());
        if (bookService.addBook(book)) {
            modelAndView.addObject("user", this.user);
            modelAndView.setViewName("home");
            return modelAndView;
        }
        modelAndView.addObject("book", book);
        modelAndView.setViewName("addBook");
        return modelAndView;
    }

    //Author controller
    @GetMapping("/addAuthor")
    public ModelAndView AddAuthorHome(ModelAndView modelAndView) {
        modelAndView.addObject("author", new Author());
        modelAndView.setViewName("addAuthor");
        return modelAndView;
    }

    @GetMapping("/getAllAuthors")
    public ModelAndView getAllAuthors(ModelAndView modelAndView) {
        List<Author> allAuthors = authorService.getAllAuthors();
        modelAndView.addObject("authors", allAuthors);
        modelAndView.setViewName("getAllAuthors");
        return modelAndView;
    }

    @GetMapping("/getAllAuthors/delete/{id}")
    public String deleteAuthor(@PathVariable(name = "id") Long id) {
        authorService.deleteAuthorById(id);
        return "redirect:/getAllAuthors";
    }


    @GetMapping("/getAllAuthors/update/{id}")
    public ModelAndView showUpdateAuthorPage(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("updateAuthor");
        Author author = authorService.getAuthorById(id);
        author.setId(id);
        modelAndView.addObject("author", author);
        return modelAndView;
    }

    @PostMapping("/getAllAuthors/updateAuthor")
    public String updateAuthor(@ModelAttribute("author") Author author) {
        if (authorService.getAuthor(author) != null) {
            return "redirect:/getAllAuthors";
        }
        authorService.updateAuthorById(author);
        return "redirect:/getAllAuthors";
    }


    @PostMapping("/addAuthorComplete")
    public ModelAndView addAuthor(@ModelAttribute("author") Author author) {
        ModelAndView modelAndView = new ModelAndView();
        if (authorService.addAuthor(author)) {
            modelAndView.addObject("user", this.user);
            modelAndView.setViewName("home");
            return modelAndView;
        }
        modelAndView.addObject("author", author);
        modelAndView.setViewName("addAuthor");
        return modelAndView;
    }

    //Client controller
    @PostMapping("/searchClientsByBirthday/result")
    public ModelAndView searchClientsByBirthdayResult(@ModelAttribute("client") Client client) {
        ModelAndView modelAndView = new ModelAndView();
        List<Client> clients = clientService.getClientsByBirthday(client);
        modelAndView.addObject("clients", clients);
        modelAndView.setViewName("getAllSearchClientsByBirthday");
        return modelAndView;
    }

    @GetMapping("/searchClientsByBirthday")
    public ModelAndView searchClientsByBirthday(ModelAndView modelAndView) {
        Client client = new Client();
        modelAndView.addObject("client", client);
        modelAndView.setViewName("searchClientsByBirthday");
        return modelAndView;
    }

    @PostMapping("/searchClientsByName/result")
    public ModelAndView searchClientsByNameResult(@ModelAttribute("client") Client client) {
        ModelAndView modelAndView = new ModelAndView();
        List<Client> clients = clientService.getClientsByName(client);
        modelAndView.addObject("clients", clients);
        modelAndView.setViewName("getAllSearchClientsByName");
        return modelAndView;
    }

    @GetMapping("/searchClientsByName")
    public ModelAndView searchClientsByName(ModelAndView modelAndView) {
        Client client = new Client();
        modelAndView.addObject("client", client);
        modelAndView.setViewName("searchClientsByName");
        return modelAndView;
    }


    @GetMapping("/getAllClients/delete/{id}")
    public String deleteClient(@PathVariable(name = "id") Long id) {
        List<Book> booksByClientId = bookService.getBooksByClientId(id);
        for (Book book : booksByClientId) {
            bookService.updateBookByClient(book, 1L);
            bookService.updateBookByUser(book, 1L);
        }
        clientService.deleteClientById(id);
        return "redirect:/getAllClients";
    }


    @GetMapping("/getAllClients/update/{id}")
    public ModelAndView showUpdateClientPage(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("updateClient");
        Client client = clientService.getClientById(id);
        client.setId(id);
        modelAndView.addObject("client", client);
        return modelAndView;
    }

    @PostMapping("/getAllClients/updateClient")
    public String updateClient(@ModelAttribute("client") Client client) {
        if (clientService.getClient(client) != null) {
            return "redirect:/getAllClients";
        }
        clientService.updateClientById(client);
        return "redirect:/getAllClients";
    }

    @GetMapping("/getAllClients")
    public ModelAndView getAllClients(ModelAndView modelAndView) {
        List<Client> allClients = clientService.getAllClients();
        for (int i = 0; i < allClients.size(); i++) {
            if (allClients.get(i).getId() == 1) {
                allClients.remove(i);
            }
        }
        modelAndView.addObject("clients", allClients);
        modelAndView.setViewName("getAllClients");
        return modelAndView;
    }

    @GetMapping("/addClient")
    public ModelAndView AddClientHome(ModelAndView modelAndView) {
        modelAndView.addObject("client", new Client());
        modelAndView.setViewName("addClient");
        return modelAndView;
    }

    @PostMapping("/addClientComplete")
    public ModelAndView addAuthor(@ModelAttribute("client") Client client) {
        ModelAndView modelAndView = new ModelAndView();
        if (clientService.addClient(client)) {
            modelAndView.addObject("user", this.user);
            modelAndView.setViewName("home");
            return modelAndView;
        }
        modelAndView.addObject("client", client);
        modelAndView.setViewName("addClient");
        return modelAndView;
    }

    //Publishing house controller
    @GetMapping("/getAllPublishingHouses/delete/{id}")
    public String deletePublishingHouse(@PathVariable(name = "id") Long id) {
        publishingHouseService.deletePublishingHouseById(id);
        return "redirect:/getAllPublishingHouses";
    }


    @GetMapping("/getAllPublishingHouses/update/{id}")
    public ModelAndView showUpdatePublishingHousePage(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("updatePublishingHouse");
        PublishingHouse publishingHouse = publishingHouseService.getPublishingHouseById(id);
        publishingHouse.setId(id);
        modelAndView.addObject("publishingHouse", publishingHouse);
        return modelAndView;
    }

    @PostMapping("/getAllPublishingHouses/updatePublishingHouse")
    public String updatePublishingHouse(@ModelAttribute("publishingHouse") PublishingHouse publishingHouse) {
        if (publishingHouseService.getPublishingHouse(publishingHouse) != null) {
            return "redirect:/getAllPublishingHouses";
        }
        publishingHouseService.updatePublishingHouseById(publishingHouse);
        return "redirect:/getAllPublishingHouses";
    }

    @GetMapping("/getAllPublishingHouses")
    public ModelAndView getAllPublishingHouses(ModelAndView modelAndView) {
        List<PublishingHouse> publishingHouses = publishingHouseService.getAllPublishingHouses();
        modelAndView.addObject("publishingHouses", publishingHouses);
        modelAndView.setViewName("getAllPublishingHouses");
        return modelAndView;
    }


    @GetMapping("/addPublishingHouse")
    public ModelAndView addPublishingHouseHome(ModelAndView modelAndView) {
        modelAndView.addObject("publishingHouse", new PublishingHouse());
        modelAndView.setViewName("addPublishingHouse");
        return modelAndView;
    }

    @PostMapping("/addPublishingHouseComplete")
    public ModelAndView addPublishingHouse(
            @ModelAttribute("publishingHouse") PublishingHouse publishingHouse) {
        ModelAndView modelAndView = new ModelAndView();
        if (publishingHouseService.addPublishingHouse(publishingHouse)) {
            modelAndView.addObject("user", this.user);
            modelAndView.setViewName("home");
            return modelAndView;
        }
        modelAndView.addObject("publishingHouse", publishingHouse);
        modelAndView.setViewName("addPublishingHouse");
        return modelAndView;
    }

    //Accounting controller
    @GetMapping("/getAllAccounting")
    public ModelAndView getAllAccounting(ModelAndView modelAndView) {
        List<Accounting> allAccounting = accountingService.getAllAccounting();
        if (allAccounting == null) {
            modelAndView.setViewName("getAllAccounting");
            return modelAndView;
        }
        for (Accounting accounting : allAccounting) {
            Book book = bookService.getBookById(accounting.getBookId());
            accounting.setBook(bookCompleted(book));
            Client client = clientService.getClientById(accounting.getClientId());
            accounting.setClient(client);
            User user = userService.getUserById(accounting.getUserId());
            accounting.setUser(user);
        }
        Collections.reverse(allAccounting);
        modelAndView.addObject("accounting", allAccounting);
        modelAndView.setViewName("getAllAccounting");
        return modelAndView;
    }


    private Book bookCompleted(Book book) {
        Author author = authorService.getAuthorById(book.getAuthorId());
        book.setAuthor(author);
        PublishingHouse publishingHouse = publishingHouseService.getPublishingHouseById(book.getPublishingHouseId());
        book.setPublishingHouse(publishingHouse);
        Client client = clientService.getClientById(book.getClientId());
        book.setClient(client);
        User user = userService.getUserById(book.getUserId());
        book.setUser(user);
        return book;
    }

    private Client defaultClient() {
        Client client = new Client();
        client.setId(1L);
        client.setBirthday("");
        client.setLastname("");
        client.setFirstname("");
        return client;
    }

    private User defaultUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstname("");
        user.setLastname("");
        user.setEmail("");
        return user;
    }

    private String currentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date) + "";
    }

}
