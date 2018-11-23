
package a.plus.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import utilities.internal.SchemaPrinter;

import com.google.gson.Gson;

import domain.Section;
import domain.Tutorial;

public class JsonUse {

	public static void main(final String[] args) {
		final Tutorial t = new Tutorial();

		final Section s1 = new Section();
		s1.setNumber(1);
		s1.setPhotoURL("http:///www.photos1.com");
		s1.setText("Section 1");
		s1.setTitle("Section 1");

		final Section s2 = new Section();
		s1.setNumber(2);
		s1.setPhotoURL("http:///www.photos2.com");
		s1.setText("Section 2");
		s1.setTitle("Section 2");

		final Collection<Section> sections = new ArrayList<>();
		sections.add(s1);
		sections.add(s2);

		t.setLastUpdate(new Date(2018, 10, 28));
		t.setPhotoURL("http://www.photourl.com");
		t.setSections(sections);
		t.setSummary("Summary");
		t.setTitle("Test Tutorial");

		//Creating Gson Object
		final Gson gson = new Gson();

		//Class Tutorial to Json
		final String json = gson.toJson(t);
		//Print Json
		System.out.println("Printing Json");
		System.out.println(json);
		//Json to class
		final Tutorial t2 = gson.fromJson(json, Tutorial.class);
		//Print Class
		System.out.println("Tutorial");
		final SchemaPrinter printer = new SchemaPrinter();
		SchemaPrinter.print(t2);
		SchemaPrinter.print(sections);
	}
}
