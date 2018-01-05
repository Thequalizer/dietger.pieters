package dietgerpieters.werkstuk.TypeConverters;

import org.junit.Test;

import dietgerpieters.werkstuk.Models.Wedstrijd;

import static org.junit.Assert.*;

/**
 * Created by Dietger (Pantani) on 5/01/2018.
 */
public class CategorieConverterTest {
    @Test
    public void toCategorie() throws Exception {
        String input = "Profs";
        Wedstrijd.Categorie output;
        Wedstrijd.Categorie expected = Wedstrijd.Categorie.PROFS;

        output = CategorieConverter.toCategorie(input);

        assertEquals(expected,output);

    }

    @Test
    public void toCategorieDbValue() throws Exception {
        Wedstrijd.Categorie input = Wedstrijd.Categorie.PROFS;
        String output;
        String expected = "PROFS";

        output = CategorieConverter.toCategorieDbValue(input);

        assertEquals(expected,output);
    }

}