package edu.levytskyi.lab1cloud.controller; // Заміни на свій пакет

import edu.levytskyi.lab1cloud.model.Item; // Заміни на свій пакет
import edu.levytskyi.lab1cloud.repository.ItemRepository; // Заміни на свій пакет
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Залишаємо RestController, бо він вже є
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    // CREATE: Створення нового Item
    @PostMapping("/items")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        try {
            Item newItem = itemRepository.save(new Item(item.getName(), item.getDescription()));
            return new ResponseEntity<>(newItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ ALL: Отримання всіх Items
    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems() {
        try {
            List<Item> items = itemRepository.findAll();
            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Отримання Item за ID
    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable("id") long id) {
        Optional<Item> itemData = itemRepository.findById(id);

        // Якщо itemData існує, повертаємо його, інакше - 404 Not Found
        return itemData.map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Оновлення Item за ID
    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable("id") long id, @RequestBody Item item) {
        Optional<Item> itemData = itemRepository.findById(id);

        if (itemData.isPresent()) {
            Item _item = itemData.get();
            _item.setName(item.getName());
            _item.setDescription(item.getDescription());
            return new ResponseEntity<>(itemRepository.save(_item), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Видалення Item за ID
    @DeleteMapping("/items/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") long id) {
        try {
            itemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Успішний успіх
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE ALL: Ви думаєте, що весь світ має підкорятися вам лише тому, що маєте атомну бомбу? Насправді ви слабкі, ви боїтеся навіть власної сили.
    @DeleteMapping("/items")
    public ResponseEntity<HttpStatus> deleteAllItems() {
        try {
            itemRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}