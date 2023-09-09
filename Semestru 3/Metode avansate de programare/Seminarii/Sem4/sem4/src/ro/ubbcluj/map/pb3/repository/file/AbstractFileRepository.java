package ro.ubbcluj.map.pb3.repository.file;

import ro.ubbcluj.map.pb3.domain.Entity;
import ro.ubbcluj.map.pb3.domain.validators.Validator;

import ro.ubbcluj.map.pb3.repository.memory.InMemoryRepository;


import java.io.*;

import java.util.Arrays;
import java.util.List;


///Aceasta clasa implementeaza sablonul de proiectare Template Method; puteti inlucui solutia propusa cu un Factori (vezi mai jos)
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID,E> {
    String fileName;
    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName = fileName;
        loadData();

    }

    private void loadData() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String linie;

            while ((linie = bufferedReader.readLine()) != null) {
                List<String> attributes = Arrays.asList(linie.split(";"));
                //System.out.println("ok");

                E entity = extractEntity(attributes);
                super.save(entity);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *  extract entity  - template method design pattern
     *  creates an entity of type E having a specified list of @code attributes
     * @param attributes
     * @return an entity of type E
     */
    public abstract E extractEntity(List<String> attributes);
    ///Observatie-Sugestie: in locul metodei template extractEntity, puteti avea un factory pr crearea instantelor entity

    protected abstract String createEntityAsString(E entity);

    @Override
    public E save(E entity){
        if (super.save(entity) != null) {
            return entity;
        }
        writeToFile(entity);
        return null;
    }

    @Override
    public E update(E entity) {
        if (super.update(entity) != null)
            return entity;


        return  null;
    }
/*
    private void writeEntities() {
        try (BufferedWriter)
    }*/

    protected void writeToFile(E entity){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true))) {
            bufferedWriter.write(createEntityAsString(entity));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

