# Sudoku Game Base

## Overview

This project is a group assignment for university studies. It focuses on implementing and analyzing a Sudoku game, a popular puzzle game that tests logical thinking.

## Features

- **Sudoku Game Implementation**
  - Implements a fully functional Sudoku game with features such as puzzle generation and user interaction.
  - Allows for both manual input and automatic puzzle solving using a backtracking algorithm.
- **Game Interface and Interaction**
  - Provides a user-friendly graphical interface for puzzle-solving.
- **Puzzle Difficulty Levels**
  - Supports multiple difficulty levels: Easy, Medium, Hard.
  - Puzzle difficulty is determined by the number of pre-filled cells and the complexity of the solution.
- **Game Save and Load Feature**
  - Allows users to save their current game progress (board state, moves) to a database.
  - Provides the ability to load previously saved games and resume from where the player left off.
  - Game data is stored in a relational database (MySQL) to ensure easy access and data management.

## Output

The program generates:

- A Sudoku game board that can be solved manually
- A difficulty selector with automatic puzzle generation.
- Ability to save and load games from a database, ensuring players can resume their puzzles at any time.

## Dataset

This project generates and solves puzzles in the standard 9x9 Sudoku grid format, with different difficulty levels based on the number of pre-filled cells. Game progress is stored in a relational database, which includes the game board and other relevant details.

## License

This project is open-source and available under the MIT License.

## Authors

This project was developed as a group assignment by:

- Edyta Szymańska
- Alicja Bartczak
