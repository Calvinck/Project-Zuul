public class Writer{
    public String writeBook(String book){
        
        if(book.equals("book_of_knowlage")){
            return "You are probably wondering \"Where am I, how do i get out of here?\"\nWell I do have just the answer for you. First, you have to find the drill\nBut to get out, you have to riddle me this:\n\nA man is standing in front of a painting of a man, and he tells us the following:\nBrothers and sisters have I none, but this man's father is my father's son.\nWho is on the painting?\nIs this man his father? Drill at the north side.\nIs this man he himself? Drill at east side\nIs this man his son? Drill at the south side.\nIs this man his brother? Drill at the west side.\n\nYou are probably wondering \"What about my Maserati, where is the cash?\"\nAnother riddle for you my brother:\n\nA hunter leaves his cabin early in the morning and walks one mile due south.\nHere he sees a bear and starts chasing it for one mile due east before he is able to shoot the bear.\nAfter shooting the bear, he drags it one mile due north back to his cabin where he started that morning.\nWhat color is the bear?\nFor the cash, drill north if you think the bear was white.\nFor the cash, drill east if you think the bear was grey.\nFor the cash, drill west if you think the bear was black.\nFor the cash, drill south if you think the bear was brown.";
        }
        else if(book.equals("bank_book")){
            return "Chapter 1: Getting Started\n 1: Determine a need.\n 2: Appoint a board of directors.\n 3: Make sure you have the starting capital.\n 4: Create a business summary plan.\n 5: Hire a legal team.\n 6: Establish a risk management infrastructure.\n 7: Hire a public face.\n 8: Apply for all charters.\n\nChapter 2: Making it happen\n 1: Find a place.\n 2: Purchase the space.\n 3: Come up with an elevator speech.\n 4: Establish the appropriate relationships.\n 5: Establish what the bank will offer.\n 6: Build a trap for robbers who are trying to enter the safe.\n 7: Monitor your cashflow.\n 8: Invest in your community.\n 9: Establish an online banking option.\n 10: Hire excellent employees.";
        }
        else{return null;}
    }
}