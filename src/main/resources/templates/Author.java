//package net.conferencescheduling.spring.model.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Entity
//@Table(name="author")
//public class Author {
//    @Id
//    @SequenceGenerator(name="seq_gen",sequenceName = "seq_gen",initialValue = 100,allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_gen")
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "surname")
//    private String surname;
//
//    @JsonManagedReference
//    @JsonIgnore
//    @ManyToMany(mappedBy = "authors",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Paper> papers= new ArrayList<>();
//
//
//}
