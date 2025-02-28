package komponentowe;

import komponentowe.exception.DaoException;
import komponentowe.exception.FileOperationException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    private String directory;


    public FileSudokuBoardDao(String directory) {
        this.directory = directory;
        File f = new File(this.directory);
        if (!f.exists()) {
            f.mkdirs();
        }
    }


    @Override
    public SudokuBoard read(String name) throws DaoException {
        File file = new File(directory, name); //tworzy plik nazwe w katalogu directory

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            SudokuBoard board = (SudokuBoard) ois.readObject();
            return board;
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void write(String name, SudokuBoard board) throws DaoException {
            try (FileOutputStream fos = new FileOutputStream(directory + "/" + name)) {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(board);
            } catch (IOException e) {
                throw new DaoException(e);
            }

    }

    @Override public List<String> names() {
        File dir = new File(directory);
        List<String> fileNames = new ArrayList<>();
        for (String file : dir.list()) {
            fileNames.add(file);
        }
        return fileNames;
    }

    @Override
    public void close() throws FileOperationException {

    }
}