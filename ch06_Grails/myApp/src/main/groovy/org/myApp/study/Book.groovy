package org.myAp.study

import grails.persistence.*

@Entity
class Book{
    Reader reader
    String isbn
    String title
    String author
    String description
}