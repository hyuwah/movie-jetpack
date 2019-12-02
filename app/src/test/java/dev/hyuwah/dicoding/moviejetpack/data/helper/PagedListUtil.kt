package dev.hyuwah.dicoding.moviejetpack.data.helper

import androidx.paging.PagedList
import io.mockk.every
import io.mockk.mockk

object PagedListUtil {

    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = mockk<PagedList<T>>()
        every { pagedList[any() as Int] }.answers { list[arg(0)] }
        every { pagedList.size }.returns(list.size)
        return pagedList
    }
}