package dicewarsSpring.Resource;

import dicewarsSpring.ModelRepo.Field;
import dicewarsSpring.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardResource {

    @Autowired
    public BoardService boardService;

    @GetMapping("/board")
    public Field[] initializeBoard() {
        return boardService.initializeBoard();
    }
}
