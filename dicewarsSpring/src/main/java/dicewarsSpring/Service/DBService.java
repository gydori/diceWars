package dicewarsSpring.Service;

import dicewarsSpring.Model.Board;
import dicewarsSpring.Model.Field;
import dicewarsSpring.Repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBService {
    @Autowired
    public Board board;

    @Autowired
    FieldRepository fieldRepo;

    public void saveBoard() {
        for (int i = 0; i < Math.pow(board.getSize(), 2); i++) {
            fieldRepo.save(board.getField(i));
        }
    }

    public void clearDB() {
        fieldRepo.deleteAll();
    }

    public List<Field> findAllField() {
        return fieldRepo.findAll();
    }


}
