@file:Suppress("NAME_SHADOWING")

package cinema

import kotlin.math.ceil

fun main() {

    print("Enter the number of rows:\n> ")
    val numRows = readln().toInt()
    print("Enter the number of seats in each row:\n> ")
    val numSeatsPerRow = readln().toInt()
    val totalNumSeats = numRows * numSeatsPerRow
    val seats = Array(numRows) { BooleanArray(numSeatsPerRow) }
    var numPurchasedTickets = 0
    var currentIncome = 0
    var run = true

    fun showSeats() {
        println("Cinema:")
        print("  ")
        for (seatNum in 1..numSeatsPerRow) {
            print("$seatNum ")
        }
        println()
        for (rowNum in 1..numRows) {
            print("$rowNum ")
            for (seatNum in 1..numSeatsPerRow) {
                val symbol = when {
                    seats[rowNum - 1][seatNum - 1] -> "B"
                    else -> "S"
                }
                print("$symbol ")
            }
            println()
        }
    }

    fun calculateTicketPrice(rowNum: Int): Int {
        val totalNumSeats = numRows * numSeatsPerRow
        val frontHalfPrice = 10
        val backHalfPrice = 8
        return if (totalNumSeats <= 60 || rowNum <= numRows / 2) {
            frontHalfPrice
        } else backHalfPrice
    }

    fun buyTicket() {
        print("Enter a row number:\n> ")
        val rowNum = readln().toInt()
        print("Enter a seat number in that row:\n> ")
        val seatNum = readln().toInt()
        when {
            rowNum !in 1..numRows || seatNum !in 1..numSeatsPerRow -> {
                println("Wrong input!")
                return buyTicket()
            }

            seats[rowNum - 1][seatNum - 1] -> {
                println("That ticket has already been purchased!")
                return buyTicket()
            }

            else -> {
                seats[rowNum - 1][seatNum - 1] = true
                val ticketPrice = calculateTicketPrice(rowNum)
                numPurchasedTickets++
                currentIncome += ticketPrice
                println("Ticket price: \$$ticketPrice")
            }
        }
    }

    fun calculateTotalIncome(): Int {
        val totalNumSeats = numRows * numSeatsPerRow
        val frontHalfPrice = 10
        val backHalfPrice = 8
      //  val middleRowNum = ceil(numRows / 2.0).toInt()
        return if (totalNumSeats <= 60) {
            totalNumSeats * frontHalfPrice
        } else {
            val frontHalfNumSeats = numRows / 2 * numSeatsPerRow
            val backHalfNumSeats = totalNumSeats - frontHalfNumSeats
            frontHalfNumSeats * frontHalfPrice + backHalfNumSeats * backHalfPrice
        }
    }

    fun showStatistics() {
        val percentage = numPurchasedTickets.toDouble() / totalNumSeats * 100
        val formattedPercentage = "%.2f".format(percentage)
        println("Number of purchased tickets: $numPurchasedTickets")
        println("Percentage: $formattedPercentage%")
        println("Current income: \$$currentIncome")
        println("Total income: \$${calculateTotalIncome()}")
    }

    while (run) {
        println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit")
        when (readln()) {
            "1" -> showSeats()
            "2" -> buyTicket()
            "3" -> showStatistics()
            "0" -> run = false
        }
    }
}
