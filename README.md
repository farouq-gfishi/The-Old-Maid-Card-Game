# Old Maid Card Game - Java Multithreading Implementation

## Project Description
This project is a Java implementation of the Old Maid card game using multithreading, where each player is represented by a separate thread. The game follows the standard rules of Old Maid with a 52-card deck plus one Joker card.

## Game Rules
- The game is played with 2 or more players
- Cards are dealt equally to all players
- Players discard matching pairs (same value and color: Spades+Clubs or Diamonds+Hearts)
- Players take turns drawing random cards from other players
- The player left with the Joker at the end loses the game

## Implementation Details
- Uses Java multithreading with wait/notify mechanism for synchronization
- Follows Object-Oriented Programming principles
- Implements the Mediator pattern to manage player-game interactions
- Clean code principles followed (SOLID, DRY, YAGNI)

## Class Structure
1. `GameRunner`: Entry point for the application
2. `Game`: Main game logic and thread management
3. `Player`: Thread class representing each player
4. `Deck`: Creates and manages the card deck
5. `Card`: Represents individual playing cards
6. `Mediator`: Handles communication between Game and Players

## How to Run
1. Compile all Java files
2. Run `GameRunner` class
3. Enter the number of players when prompted
4. The game will run automatically and display the results

## Output
The program displays:
- Initial card distribution
- Matching pairs discarded by each player
- Card draws between players
- Players being eliminated
- The final loser (player left with Joker)
