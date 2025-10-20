package com.lab2.app;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.lab2.app.Ex_1.IntegerAscendingComparator;
import com.lab2.app.Ex_1.MyCollections;
import com.lab2.app.Ex_2.PersonalData;
import com.lab2.app.Ex_2.PersonalDataAdapter;
import com.lab2.app.Ex_3.Sensor;
import java.time.LocalDate;
/**
 * Unit test for simple App.
 */
public class AppTest {


    @Test
    public void testEx1() {
        // Test with implicit Comparable (natural ordering)
        Integer[] arr1 = {5, 2, 8, 1, 9, 3};
        Integer[] expected1 = {1, 2, 3, 5, 8, 9};
        MyCollections.sort(arr1);

        System.out.println("Sorted with implicit Comparable:");
        printArray(arr1);
        assertArrayEquals(expected1, arr1);

        // Test with my custom integer ascending comparator
        Integer[] arr2 = {5, 2, 8, 1, 9, 3};
        Integer[] expected2 = {1, 2, 3, 5, 8, 9};
        
        MyCollections.sort(arr2, new IntegerAscendingComparator());

        System.out.println("Sorted with IntegerAscendingComparator:");
        printArray(arr2);
        assertArrayEquals(expected2, arr2);

        // Compare results - both should produce the same output
        System.out.println("Both methods produce the same result: " +
                          java.util.Arrays.equals(arr1, arr2));
        assertArrayEquals(arr1, arr2);
    }

    @Test
    public void testEx2(){
        // Create PersonalData object
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        PersonalData personalData = new PersonalData(
            "John Doe",
            "john.doe@example.com",
            birthDate,
            "+1234567890"
        );

        // Wrap it with the adapter
        PersonalDataAdapter adapter = new PersonalDataAdapter(personalData);

        // Get the JSON representation
        String jsonOutput = adapter.getPersonalInformation();

        System.out.println("Personal Information as JSON:");
        System.out.println(jsonOutput);

        // Verify the output is not null and contains expected fields
        assertNotNull(jsonOutput);
        assertTrue(jsonOutput.contains("\"name\":\"John Doe\""));
        assertTrue(jsonOutput.contains("\"email\":\"john.doe@example.com\""));
        assertTrue(jsonOutput.contains("\"telephone\":\"+1234567890\""));
        assertTrue(jsonOutput.contains("\"bday\""));
    }

    
    
    private void printArray(Integer[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
