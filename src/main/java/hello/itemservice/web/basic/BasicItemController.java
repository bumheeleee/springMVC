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


    //상품 등록V1
    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "/basic/item";
    }

    /**
     @ModelAttribute의 기능

     @ModelAttribute - 요청 파라미터 처리
     @ModelAttribute 는 Item 객체를 생성하고, 요청 파라미터의 값을 프로퍼티 접근법(setXxx)으로
     입력해준다.

     @ModelAttribute - Model 추가
     @ModelAttribute 는 중요한 한가지 기능이 더 있는데, 바로 모델(Model)에 @ModelAttribute 로
     지정한 객체를 자동으로 넣어준다. 지금 코드를 보면 model.addAttribute("item", item) 가 주석처리
     되어 있어도 잘 동작하는 것을 확인할 수 있다.
     */
    //상품 등록V2
    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model){

        itemRepository.save(item);
//      model.addAttribute("item", item); ModelAttribute("item")의 "item"으로 model에 자동으로 추가되서,생략 가능

        return "/basic/item";
    }


    //상품 등록V3
    //@ModelAttribute의 ()도 생략가능
    //생략하면 객체 클래스에 첫글자만 소문자로 바꿔서, 대입됨
    //ex) Item -> item
    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        itemRepository.save(item);
        return "/basic/item";
    }


    //상품 등록V4
    //@ModelAttribute도 생략이 가능하다.
    @PostMapping("/add")
    public String addItemV4(Item item){
        itemRepository.save(item);
        return "/basic/item";
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
