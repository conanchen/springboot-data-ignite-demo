package org.ditto.cloud;

import org.ditto.cloud.model.Breed;
import org.ditto.cloud.model.Dog;
import org.ditto.cloud.repository.BreedRepository;
import org.ditto.cloud.repository.DogRepository;
import org.ditto.cloud.repository.SpringConfig;
import org.apache.ignite.Ignite;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Date;
import java.util.List;

/**
 * Hello world!
 *
 */
public class ApplicationSpring
{
    private static AnnotationConfigApplicationContext ctx;
    private static BreedRepository breedRepository;
    private static DogRepository dogRepository;
    private static Ignite ignite;

    public static void main( String[] args )
    {
        System.out.println( "Spring Data Example!" );
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(SpringConfig.class);
        ctx.refresh();

        breedRepository = ctx.getBean(BreedRepository.class);
        dogRepository = ctx.getBean(DogRepository.class);
        ignite = ctx.getBean(Ignite.class);

        //fill the repository with data and Save
        Breed collie = new Breed();
        collie.setId(1L);
        collie.setName("collie");
        //save Breed with name collie
        breedRepository.save(1L, collie);

        System.out.println("Add one breed in the repository!");
        // Query the breed
        List<Breed> getAllBreeds = breedRepository.getAllBreedsByName("collie");

        for(Breed breed : getAllBreeds){
            System.out.println("Breed:" + breed);
        }
        //Add some dogs
        Dog dina = new Dog();
        dina.setName("dina");
        dina.setId(1L);
        dina.setBreedid(1L);
        dina.setBirthdate(new Date(System.currentTimeMillis()));
        //Save Dina
        dogRepository.save(2L,dina);
        System.out.println("Dog dina save into the cache!"+ignite);
        //Query the Dog Dina
        List<Dog> dogs = dogRepository.getDogByName("dina");
        for(Dog dog : dogs){
            System.out.println("Dog:"+ dog);
        }

    }
}