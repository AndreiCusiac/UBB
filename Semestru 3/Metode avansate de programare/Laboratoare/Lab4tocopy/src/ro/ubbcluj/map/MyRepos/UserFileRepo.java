package ro.ubbcluj.map.MyRepos;

import ro.ubbcluj.map.domain.MyModels.User;
import ro.ubbcluj.map.domain.MyValidators.UserRepoValidator;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class UserFileRepo extends UserRepo{
    private String fileName;

    /**
     * Constructor UserFileRepo
     * @param fileName, numele fisierului cu Useri
     * @throws UserRepoValidator, daca apar probleme la incarcarea User-ilor
     */
    public UserFileRepo(String fileName) throws UserRepoValidator {
        super();
        this.fileName = fileName;
        loadData();
    }

    /**
     * citeste datele din fisier, construieste Useri si ii adauga in obiectul extins
     * @throws UserRepoValidator, daca apar probleme
     */
    private void loadData() throws UserRepoValidator {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                List<String> attributes = Arrays.asList(line.split(";"));

                User user = readUser(attributes);
                super.add(user);
            }

        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            File file = new File(fileName);
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * construieste si returneaza un User pe baza unor String-uri
     * @param attributes
     * @return user-ul creat
     * @throws UserRepoValidator, daca apar probleme
     */
    private User readUser(List<String> attributes) throws UserRepoValidator {
        if (attributes.size() == 3) {
            return new User(attributes.get(0), attributes.get(1), attributes.get(2));
        }
        else if (attributes.size() == 4)
        {
            return new User(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3));
        }
        throw new UserRepoValidator("Eroare la citirea User-ilor!");
    }

    /**
     * salveaza User-ul in obiectul extins si rescrie fisierul
     * @param user
     * @throws UserRepoValidator, daca apar probleme la salvare
     */
    @Override
    public void add(User user) throws UserRepoValidator {
        super.add(user);
        writeToFile();
    }

    /**
     * functie de scriere a User-ilor in fisier
     */
    private void writeToFile() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.print("");
        writer.close();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true))) {
            for (var x : this.getAll()) {
                bufferedWriter.write(writableUser(x));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * construieste un String pentru scrierea in fisier pe baza unui User
     * @param x
     * @return String-ul unui user
     */
    private String writableUser(User x) {
        return new String(x.getId() + ";" + x.getFirstName() + ";" + x.getLastName() + ";" + x.getEmail() + ";");
    }

    /**
     * sterge User-ul din obiectul extins si rescrie fisierul
     * @param ID
     * @throws UserRepoValidator, daca User-ul nu exista
     */
    @Override
    public void delete(String ID) throws UserRepoValidator {
        super.delete(ID);
        writeToFile();
    }
}
