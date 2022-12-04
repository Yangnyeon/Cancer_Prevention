package com.example.cancer_prevention.room

import androidx.paging.PagingSource
import androidx.paging.PagingState

/*
class CigarettePaingSource(
    private val dao: CigaretteDao
): PagingSource<Int, Cigarette>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cigarette> {
        val page = params.key ?: 1
        return try {
            val items = dao.selectList(page, params.loadSize)
            // val items = dao.getTodoContentsByPaging(page, params.loadSize)
            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + (params.loadSize / 10)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Cigarette>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
 */
