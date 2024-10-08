# f24-mvc-demo
## CRUD MVC App using JPA/Hibernate, MySQL, and ThymeLeaf.
## Things to note:
- [Dependencies](https://github.com/uncg-csc340/f24-mvc-app/blob/94d038d37146b1222997b29a827c9634bd3657f6/pom.xml#L32) to JPA, MySQL, and ThymeLeaf, in addition to the usual. JPA handles the persistence, MySQL is the database to be used, ThymeLeaf generates HTML templates.
- [`/src/main/resources/application.properties`](https://github.com/uncg-csc340/f24-mvc-demo/blob/94d038d37146b1222997b29a827c9634bd3657f6/src/main/resources/application.properties) file  is the configuration for the MySQL database.
  - the database name is between the last `/` and the `?`. In this case the database name is `f24-340`.
  - You MUST have the database up and running before running the project! 
    - Open your XAMPP Control Panel.
    - Start the Apache server.
    - Start MySQL.
    - Click on MySQL "Admin" to open up the DBMS.
    - Ensure the database that you need is available.
- [Entity](https://github.com/uncg-csc340/f24-mvc-demo/blob/94d038d37146b1222997b29a827c9634bd3657f6/src/main/java/com/csc340/mvc_demo/student/Student.java#L5)
  - The Student class is annotated as an `@Entity `. This is used by Hibernate (an implementation of the Jakarta Persistence API) to map class attributes to database tables and SQL types.
  - We also annotated with `@Table` to give Hibernate directions to use this specific table name. This is optional but it helps with naming conventions.
  - Any Entity must have at least one attribute that is annotated as an `@Id`. In our case it's conveniently the `id` attribute.
    - We are also using an autogeneration strategy for the ID. This way we are not manually assigning IDs to our students. This is optional.
  - An Entity must have a no-argument constructor.
- [Repository](https://github.com/uncg-csc340/f24-mvc-demo/blob/94d038d37146b1222997b29a827c9634bd3657f6/src/main/java/com/csc340/mvc_demo/student/StudentRepository.java)
  - We are using an extension of the JPA Repository that comes with prebuilt database operations such as select all, select by id, select by any other reference, insert, delete, etc.
  - Annotate it as a `@Repository`.
  - We parametrize this using our object and its ID type.
    - `public interface StudentRepository extends JpaRepository<Student, Integer>` => We want to apply the JPA repository operations on the `Student` type. The `Student` has an ID of type `int`.
  - If we need special database queries that are not the standard ones mentioned above, we can create a method with a special purpose query as shown. This is an interface so no implementation body.
- [Controller](https://github.com/uncg-csc340/f24-mvc-demo/blob/94d038d37146b1222997b29a827c9634bd3657f6/src/main/java/com/csc340/mvc_demo/student/StudentController.java)
  - Note this is a `@Controller` and not a `@RestController`. This is the MVC Controller that returns views instead of objects.
  - All the mappings under this controller will start with `/students`.
  - Return view names.
  - Return `"redirect:/link/to/redirect"` - if there is not necessarily a view attached to an action. e.g. going back to list after deleting one item.
  - Model attribute names and objects using `model.addAttribute("studentList", service.getAllStudents());`
  - All views live in `src/main/resources/templates`.
    - You can also create subfolders in the templates folder. For example if you wanted views for the student to be in one folder you can create a `students` subfolder and put all the student views in there. Then your Controller would have to `return "students/students-list";` 
  - The Service class is [`@Autowired`](https://github.com/uncg-csc340/f24-mvc-demo/blob/94d038d37146b1222997b29a827c9634bd3657f6/src/main/java/com/csc340/mvc_demo/student/StudentController.java#L14). Do not use a constructor, this will not work.
    - In the Service class,the Repository class is also [`@Autowired`](https://github.com/uncg-csc340/f24-mvc-demo/blob/94d038d37146b1222997b29a827c9634bd3657f6/src/main/java/com/csc340/mvc_demo/student/StudentService.java#L15)  :)
- Views
  - [`data-th-each="student : ${studentList}"`](https://github.com/uncg-csc340/su24-jpa-demo/blob/2b1e993775bc25f77e141689b8679d39c0bd840f/src/main/resources/templates/student-list.html#L45) => "For each student in studentList, make a table row"
  - `<p data-th-text="${student.id}"></p>` => "The text content for this this paragraph element should be whatever the student id is".
  - [`<a data-th-href="@{/students/{studentId} (studentId=${student.studentId})}" data-th-text="${student.studentId}"></a>`](https://github.com/uncg-csc340/f24-mvc-demo/blob/94d038d37146b1222997b29a827c9634bd3657f6/src/main/resources/templates/student-list.html#L44) => Make a link that includes a variable in the path. Here the variabe `studentId` is the student id. The text shown by the link is also the student id (otherwise it will be bank and you cannot click on anything).We are using the @ notation because we need access to the objects. Other links that do not include data from the model can be made the regular HTML way.
  - For any form that sends POST requests with a form body, the input attribute "name" should match the data field. E.g for the student major [`<input type="text" id="major" name="major" placeholder="Major"/>`](https://github.com/uncg-csc340/f24-mvc-demo/blob/94d038d37146b1222997b29a827c9634bd3657f6/src/main/resources/templates/student-list.html#L54) we use `name="major"` to use the setters to match that field. If you do not include the input name attribute, a null will be insterted for that field.
  - Remember that any view must have a correspoding mapping.
    - If you wanted to have a page that is a standalone form, you must make a mapping in the controller that returns the form page as a view.
    - In this example, the form for creating a new student comes with the list view so this is not necessary.
    - However, the form for updating a Student is different. We add a [`@GetMapping`](https://github.com/uncg-csc340/f24-mvc-demo/blob/94d038d37146b1222997b29a827c9634bd3657f6/src/main/java/com/csc340/mvc_demo/student/StudentController.java#L93) for displaying the form.
    - Clicking on Edit for any student will hit this endpoint and return the form as a view.
    - Note we also use the model here to carry the Student data that we want to update so it will be shown on the form.
    - Saving the updated data calls the corresponding POST mapping.
      - NB: Saving and Updating in Hibernate uses the same method. If an entity exists it gets updated, else it gets created. You don't need to worry too much about it.
       ```
         @Transactional
         public <S extends T> S save(S entity) {
           if (this.entityInformation.isNew(entity)) {
              this.em.persist(entity);
              return entity;
            } else {
              return this.em.merge(entity);
          }
         }
        ```
- Run the app in IntelliJ, then on the browser go to: `http://localhost:8080/students/all`
