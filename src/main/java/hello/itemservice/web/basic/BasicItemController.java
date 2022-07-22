package hello.itemservice.web.basic;


import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    //상품 목록
    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    //상품 상세
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "/basic/item";
    }

    //상품 등록(form 만 보여줌)
    @GetMapping("/add")
    public String addForm(){
        return "/basic/addForm";
    }


    /**
     * form형식으로 저장하거나 update 기능을 수행하는 controller는 redirect를 해야한다.
     * 왜냐하면 서버에 마지막으로 요청한 url이 남아있어서 새로고침을 하면
     * update하거나 save하는 행위가 계속 반복될 수 있다.
     * 따라서 다른 url로 redirect를 해줘야 된다.
     */
    //상품 등록(저장)
    @PostMapping("/add")
    public String add(@ModelAttribute Item item){
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    //상품 수정(form만 보여줌)
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "/basic/editForm";
    }

    /**
     * form형식으로 저장하거나 update 기능을 수행하는 controller는 redirect를 해야한다.
     * 왜냐하면 서버에 마지막으로 요청한 url이 남아있어서 새로고침을 하면
     * update하거나 save하는 행위가 계속 반복될 수 있다.
     * 따라서 다른 url로 redirect를 해줘야 된다.
     */
    //상품 수정(수정하는 기능)
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("ItemA", 1000, 1));
        itemRepository.save(new Item("ItemB", 2000, 2));
    }
}
