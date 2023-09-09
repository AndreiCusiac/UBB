package ro.ubbcluj.map.MyRepos;

import ro.ubbcluj.map.domain.MyModels.Friendship;
import ro.ubbcluj.map.domain.MyModels.User;
import ro.ubbcluj.map.domain.MyValidators.FriendshipRepoValidator;
import ro.ubbcluj.map.domain.MyValidators.UserRepoValidator;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FriendshipFileRepo extends FriendshipRepo{
    private String fileName;

    /**
     * Constructor FriendshipFileRepo
     * @param fileName
     * @throws FriendshipRepoValidator
     */
    public FriendshipFileRepo(String fileName) throws FriendshipRepoValidator {
        super();
        this.fileName = fileName;
        loadData();
    }

    /**
     * citeste datele din fisier, construieste Friendships si ii adauga in obiectul extins
     * @throws FriendshipRepoValidator, daca apar probleme
     */
    private void loadData() throws FriendshipRepoValidator {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                List<String> attributes = Arrays.asList(line.split(";"));

                Friendship friendship = readFriendship(attributes);
                super.add(friendship);
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
     * construieste si returneaza un Friendship pe baza unor String-uri
     * @param attributes
     * @return friendship-ul creat
     * @throws FriendshipRepoValidator
     */
    private Friendship readFriendship(List<String> attributes) throws FriendshipRepoValidator{
        if (attributes.size() == 2) {
            return new Friendship(attributes.get(0), attributes.get(1));
        }
        throw new FriendshipRepoValidator("Eroare la citirea Friendship-urilor!");
    }

    /**
     * salveaza Friendship-ul in obiectul extins si rescrei fisierul
     * @param friendship
     * @throws FriendshipRepoValidator, daca apar probleme la salvare
     */
    @Override
    public void add(Friendship friendship) throws FriendshipRepoValidator {
        super.add(friendship);
        writeToFile();
    }

    /**
     * functie de scriere a Friendship-urilor in fisier
     */
    private void writeToFile() throws FriendshipRepoValidator {
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
                bufferedWriter.write(writableFriendship(x));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * construieste un String pentru scrierea in fisier pe baza unui Friendship
     * @param x
     * @return String-ul unui Friendship
     */
    private String writableFriendship(Friendship x) {
        return new String(x.getId1() + ";" + x.getId2() + ";");
    }

    /**
     * sterge Friendship-ul din obiectul extins si rescrei fisierul
     * @param ID1
     * @param ID2
     * @throws FriendshipRepoValidator, daca Friendship-ul nu exista
     */
    @Override
    public void delete(String ID1, String ID2) throws FriendshipRepoValidator {
        super.delete(ID1, ID2);
        writeToFile();
    }

    @Override
    public void deleteFlagged(String ID) throws FriendshipRepoValidator {
        super.deleteFlagged(ID);
        writeToFile();
    }
}
