package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TrelloMapperTestSuite {
    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void mapToBoards() {
        // Given
        TrelloListDto trelloListDto1 = new TrelloListDto("0", "something0", true);
        TrelloListDto trelloListDto2 = new TrelloListDto("1", "something1", false);
        TrelloListDto trelloListDto3 = new TrelloListDto("2", "something2", true);
        TrelloListDto trelloListDto4 = new TrelloListDto("3", "something3", false);
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("0", "name0", List.of(trelloListDto1));
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("1", "name1", List.of(trelloListDto2,trelloListDto3,trelloListDto4));
        List<TrelloBoardDto> trelloBoardsDto = List.of(trelloBoardDto1,trelloBoardDto2);

        // When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardsDto);

        // Then
        assertEquals(2, trelloBoards.size());
    }

    @Test
    public void mapToBoardsDto() {
        // Given
        TrelloList trelloList1 = new TrelloList("0", "something0", true);
        TrelloList trelloList2 = new TrelloList("1", "something1", false);
        TrelloList trelloList3 = new TrelloList("2", "something2", true);
        TrelloList trelloList4 = new TrelloList("3", "something3", false);
        TrelloBoard trelloBoard1 = new TrelloBoard("0", "name0", List.of(trelloList1));
        TrelloBoard trelloBoard2 = new TrelloBoard("1", "name1", List.of(trelloList2,trelloList3,trelloList4));
        List<TrelloBoard> trelloBoards = List.of(trelloBoard1,trelloBoard2);

        // When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);

        // Then
        assertEquals(2, trelloBoardsDto.size());
    }

    @Test
    public void maptoCardDto() {
        // Given
        TrelloCard trelloCard = new TrelloCard("name123", "description123", "pos123", "listId123");

        // When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        // Then
        assertEquals("name123", trelloCardDto.getName());
    }

    @Test
    public void maptoCard() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name123", "description123", "pos123", "listId123");

        // When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        // Then
        assertEquals("name123", trelloCard.getName());
    }
}