import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


// Méthodes utiles
//	String.split("") : découper une chaine de caractères
//	String.concat("") : concaténation de deux chaines de caractères
//	String.toLowerCase() : convertir la chaine de caractères en minuscule
//	String.replace("a", "b") : remplacer le caractère "a" par "b" dans une chaine de caractères
//	String.trim() : enlever les espaces qui se trouvent au début et à la fin d'une chaine de caractère
//	String.startsWith("abc") : vérifier si la chaine de caractères commence par la suite de caractères "abc"


public class PopulateFromCSV {

	void populateTable(String csvFile, String tableName, String tableSchema) throws SQLException {

		// Connexion à la base de données
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dw", "username",
				"password");
		Statement statement = connection.createStatement();
		System.out.println("Connected to database");

		// Configuration du fichier CSV
		String line = "";
		String cvsSplitBy = ",";

		// Récupération des types des attributs
		String[] att = tableSchema.split(",");
		List<String> attributesTypes = new ArrayList<String>();
		for (int i = 0; i < att.length; i++) {
			String[] tmp = att[i].trim().split("\\s+");
			if (tmp.length > 1)
				attributesTypes.add(tmp[1]);
			else
				System.out.println("Attribute syntax error: " + tmp[1]);
		}

		// Création de la table dans la BD
		statement.executeUpdate("CREATE TABLE " + tableName + " (" + tableSchema + ");");
		System.out.println("Table " + tableName + " created");

		// Ouverture du fichier CSV
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			// Sauter l'en-tête
			br.readLine();

			// Lecture du fichier CSV ligne par ligne
			while ((line = br.readLine()) != null) {
				String query;
				
				// TODO
				// Récupérer les valeurs des attributs présentes 
				// dans "line" et construire une requête "INSERT" 
				// pour les insérer dans la table.
				
				System.out.println(query);
				statement.executeUpdate(query);
			}

			System.out.println("Insertion completed");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws SQLException {

		PopulateFromCSV p1 = new PopulateFromCSV();

		p1.populateTable("Chemin du fichier CSV", "Sales", "OrderID char(9) PRIMARY KEY, CustomerID varchar, ItemType varchar, SalesChannel varchar, OrderPriority varchar, OrderDate varchar, ShipDate varchar, UnitsSold int, UnitePrice float, UnitCost float, TotalRevenu float, TotalCost float, TotalProfit float");
		// p1.populateTable("Chemin du fichier CSV", "Customers", "attributs de la table Customers");

	}

}
