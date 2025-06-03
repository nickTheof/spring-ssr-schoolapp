package gr.aueb.cf.schoolspring.model;

import gr.aueb.cf.schoolspring.model.static_data.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teachers")
public class Teacher extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String vat;

    @Column(unique = true)
    private String email;

    @Column(name = "father_name")
    private String fatherName;
    private String street;

    @Column(name = "street_num")
    private String streetNum;
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;



}
