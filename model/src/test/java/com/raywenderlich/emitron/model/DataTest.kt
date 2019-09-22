package com.raywenderlich.emitron.model

import com.google.common.truth.Truth.assertThat
import com.raywenderlich.emitron.model.entity.Category
import com.raywenderlich.emitron.model.entity.Domain
import com.raywenderlich.emitron.model.utils.TimeUtils
import com.raywenderlich.emitron.model.utils.isEqualTo
import org.junit.Test
import org.threeten.bp.Clock
import org.threeten.bp.LocalDateTime
import org.threeten.bp.Month

class DataTest {

  @Test
  fun getName() {
    val attributes = Attributes(name = "Introduction to Kotlin Lambdas: Getting Started")
    val data = Data(attributes = attributes)
    data.getName() isEqualTo "Introduction to Kotlin Lambdas: Getting Started"
  }

  @Test
  fun getLevel() {
    val attributes = Attributes(level = "archived")
    val data = Data(attributes = attributes)
    data.getLevel() isEqualTo "archived"
  }

  @Test
  fun getDomain() {
    val domains = listOf(
      Data(attributes = Attributes(name = "iOS & Swift")),
      Data(attributes = Attributes(name = "Android & Kotlin"))
    )
    val domainNames = "iOS & Swift, Android & Kotlin"
    val relationShip = Relationships(domains = Contents(datum = domains))
    val data = Data(relationships = relationShip)

    data.getDomain() isEqualTo domainNames

    val data2 = Data()

    data2.getDomain() isEqualTo null
  }

  @Test
  fun getDescription() {
    val attributes = Attributes(description = "In this tutorial you will learn how to use lambda.")
    val data = Data(attributes = attributes)

    data.getDescription() isEqualTo "In this tutorial you will learn how to use lambda."
  }

  @Test
  fun getCardArtworkUrl() {
    val attributes = Attributes(cardArtworkUrl = "https://koenig-media.raywenderlich.com/")
    val data = Data(attributes = attributes)

    data.getCardArtworkUrl() isEqualTo "https://koenig-media.raywenderlich.com/"
  }


  @Test
  fun isProgressionFinished() {
    val progression = Content(datum = Data(attributes = Attributes(finished = true)))
    val relationShip = Relationships(progression = progression)
    val data = Data(relationships = relationShip)
    data.isProgressionFinished() isEqualTo true

    val data2 = Data()
    data2.isProgressionFinished() isEqualTo false
  }

  @Test
  fun getPercentComplete() {
    val attributes = Attributes(percentComplete = 10.0)
    val data = Data(attributes = attributes)
    data.getPercentComplete() isEqualTo 10

    val data2 = Data()
    data2.getPercentComplete() isEqualTo 0
  }

  @Test
  fun getProgress() {
    val attributes = Attributes(progress = 10L)
    val data = Data(attributes = attributes)
    data.getProgress() isEqualTo 10L

    val data2 = Data()
    data2.getProgress() isEqualTo 0
  }

  @Test
  fun getProgressionPercentComplete() {
    val progression = Content(datum = Data(attributes = Attributes(percentComplete = 10.0)))
    val relationShip = Relationships(progression = progression)
    val data = Data(relationships = relationShip)
    data.getProgressionPercentComplete() isEqualTo 10

    val data2 = Data()
    data2.getProgressionPercentComplete() isEqualTo 0
  }

  @Test
  fun isFinished() {
    val data = Data(attributes = Attributes(finished = true))
    data.isFinished() isEqualTo true
  }

  @Test
  fun isProLabelVisible() {
    val data = Data(attributes = Attributes(free = false, finished = false), type = "contents")
    data.isProLabelVisible() isEqualTo true

    val data2 = Data(attributes = Attributes(free = true, finished = false), type = "progressions")
    data2.isProLabelVisible() isEqualTo false
  }

  @Test
  fun getContentType() {
    val data = Data(attributes = Attributes(contentType = "screencast"))
    data.getContentType() isEqualTo ContentType.Screencast
  }

  @Test
  fun getDifficulty() {
    val data = Data(attributes = Attributes(difficulty = "advanced"))
    data.getDifficulty() isEqualTo Difficulty.Advanced
  }

  @Test
  fun getReleasedAt() {
    val today = LocalDateTime.of(2019, Month.AUGUST, 11, 2, 0, 0)


    val attributes = Attributes(releasedAt = "2019-08-08T02:00:00.000Z")
    val data = Data(attributes = attributes)
    data.getReleasedAt(withYear = false, today = today) isEqualTo TimeUtils.Day.Formatted("Aug 8")

    val now = LocalDateTime.now(Clock.systemUTC())


    val data2 = Data()
    data2.getReleasedAt(withYear = false, today = today) isEqualTo TimeUtils.Day.None

    val data3 = Data(attributes = attributes)
    data3.getReleasedAt(withYear = true, today = today) isEqualTo TimeUtils.Day.Formatted("Aug 8")

    val attributes3 = Attributes(releasedAt = "2018-08-08T02:00:00.000Z")
    val data4 = Data(attributes = attributes3)
    today.minusYears(1)
    data4.getReleasedAt(
      withYear = true,
      today = today
    ) isEqualTo TimeUtils.Day.Formatted("Aug 8 2018")

    val attributes5 = Attributes(releasedAt = "2019-08-08T00:00:00.000Z")
    val data5 = Data(attributes = attributes5)
    data5.getReleasedAt() isEqualTo "2019-08-08T00:00:00.000Z"
  }

  @Test
  fun getDurationHoursAndMinutes() {
    val attributes = Attributes(duration = 4080)
    val data = Data(attributes = attributes)

    data.getDurationHoursAndMinutes() isEqualTo (1L to 8L)
  }

  @Test
  fun getDuration() {
    val attributes = Attributes(duration = 4080)
    val data = Data(attributes = attributes)

    data.getDuration() isEqualTo (4080L)
  }

  @Test
  fun isBookmarked() {
    val data = Data().addBookmark(Content(datum = Data(id = "1")))

    data.isBookmarked() isEqualTo true

    val data2 = Data()
    data2.isBookmarked() isEqualTo false
  }

  @Test
  fun getTechnology() {
    val attributes = Attributes(technology = "Swift, iOS")
    val data = Data(attributes = attributes)
    data.getTechnology() isEqualTo "Swift, iOS"
  }

  @Test
  fun getContributors() {
    val attributes = Attributes(contributors = "Jake Wharton")
    val data = Data(attributes = attributes)
    data.getContributors() isEqualTo "Jake Wharton"
  }

  @Test
  fun isLevelArchived() {
    val attributes = Attributes(level = "archived")
    val data = Data(attributes = attributes)
    data.isLevelArchived() isEqualTo true
  }

  @Test
  fun updateRelationships() {
    val domainsAndProgressions = listOf(
      Data(id = "1", attributes = Attributes(name = "iOS & Swift"), type = "domains"),
      Data(attributes = Attributes(name = "Android & Kotlin"), type = "domains"),
      Data(id = "4", attributes = Attributes(finished = true), type = "progressions"),
      Data(attributes = Attributes(finished = false), type = "progressions"),
      Data(id = "9", type = "bookmarks")
    )

    val data = Data(
      relationships = Relationships(
        domains = Contents(
          datum = listOf(Data(id = "1"), Data(id = "1"))
        ),
        progression = Content(datum = Data(id = "4")),
        bookmark = Content(Data(id = "9"))
      )
    )
    val data2 = Data()
    val result = data.updateRelationships(domainsAndProgressions)

    result.getDomain() isEqualTo "iOS & Swift"
    result.isFinished() isEqualTo true
    result.isBookmarked() isEqualTo true

    val result2 = data2.updateRelationships(emptyList())

    result2.getDomain() isEqualTo null
    result2.isFinished() isEqualTo false
    result2.isBookmarked() isEqualTo false

    val data3 = Data()
    val result3 = data3.updateRelationships(domainsAndProgressions)

    result3.getDomain() isEqualTo null
    result3.isFinished() isEqualTo false
    result3.isBookmarked() isEqualTo false
  }

  @Test
  fun addRelationships() {
    val domainsAndProgressions = listOf(
      Data(id = "1", attributes = Attributes(name = "iOS & Swift"), type = "domains"),
      Data(attributes = Attributes(name = "Android & Kotlin"), type = "domains"),
      Data(id = "4", attributes = Attributes(finished = true), type = "progressions"),
      Data(attributes = Attributes(finished = false), type = "progressions"),
      Data(id = "9", type = "bookmarks")
    )

    val data = Data()
    val result = data.addRelationships(domainsAndProgressions)

    result.getDomain() isEqualTo "iOS & Swift, Android & Kotlin"
    result.isFinished() isEqualTo true
    result.isBookmarked() isEqualTo true

    val result2 = data.updateRelationships(emptyList())

    result2.getDomain() isEqualTo null
    result2.isFinished() isEqualTo false
    result2.isBookmarked() isEqualTo false

    val result3 = data.addRelationships(emptyList())
    result3 isEqualTo data
  }

  @Test
  fun getBookmarkId() {
    val relationships = Relationships(bookmark = Content(datum = Data(id = "1")))
    val data = Data(relationships = relationships)

    assertThat(data.getBookmarkId()).isEqualTo("1")
  }

  @Test
  fun getProgressionId() {
    val relationships = Relationships(progression = Content(datum = Data(id = "2")))
    val data = Data(relationships = relationships)

    assertThat(data.getProgressionId()).isEqualTo("2")
  }

  @Test
  fun addBookmark() {
    val relationships = Relationships()
    val data = Data(relationships = relationships)

    val bookmark = Content(datum = Data(id = "1", type = "bookmarks"))
    assertThat(data.addBookmark(bookmark)).isEqualTo(
      Data(
        relationships = Relationships(bookmark = bookmark)
      )
    )

    val data2 = Data()
    val bookmarkId = "1"
    assertThat(data2.addBookmark(bookmarkId)).isEqualTo(
      Data(
        relationships = Relationships(bookmark = bookmark)
      )
    )
  }

  @Test
  fun removeBookmark() {
    val data = Data(
      relationships = Relationships(
        progression = Content(datum = Data(id = "2")),
        bookmark = Content(datum = Data(id = "1"))
      )
    )
    assertThat(data.removeBookmark()).isEqualTo(
      Data(
        relationships = Relationships(
          progression = Content(datum = Data(id = "2"))
        )
      )
    )
  }

  @Test
  fun isTypeGroup() {
    val data = Data(type = "groups")
    assertThat(data.isTypeGroup()).isTrue()

    val data2 = Data()
    assertThat(data2.isTypeGroup()).isFalse()
  }

  @Test
  fun isTypeScreencast() {
    val data = Data(type = "groups", attributes = Attributes(contentType = "screencast"))
    assertThat(data.isTypeScreencast()).isTrue()

    val data2 = Data()
    assertThat(data2.isTypeScreencast()).isFalse()
  }

  @Test
  fun isTypeDomain() {
    val data = Data(type = "domains", attributes = Attributes(contentType = "screencast"))
    assertThat(data.isTypeScreencast()).isTrue()

    val data2 = Data()
    assertThat(data2.isTypeScreencast()).isFalse()
  }

  @Test
  fun isTypeProgression() {
    val data = Data(type = "progressions", attributes = Attributes(contentType = "screencast"))
    assertThat(data.isTypeScreencast()).isTrue()

    val data2 = Data()
    assertThat(data2.isTypeScreencast()).isFalse()
  }

  @Test
  fun isTypeBookmark() {
    val data = Data(type = "bookmarks", attributes = Attributes(contentType = "screencast"))
    assertThat(data.isTypeScreencast()).isTrue()

    val data2 = Data()
    assertThat(data2.isTypeScreencast()).isFalse()
  }

  @Test
  fun getEpisodeDuration() {
    val data = Data()
    assertThat(data.getEpisodeDuration()).isEmpty()

    val data2 = Data(attributes = Attributes(duration = 4088))
    assertThat(data2.getEpisodeDuration()).isEqualTo("01:08:08")

    val data3 = Data(attributes = Attributes(duration = 488))
    assertThat(data3.getEpisodeDuration()).isEqualTo("08:08")
  }

  @Test
  fun getGroupedData() {
    val relationships = Relationships(
      contents = Contents(
        datum = listOf(
          Data(id = "1"),
          Data(id = "2")
        )
      )
    )
    val data = Data(relationships = relationships)
    assertThat(data.getGroupedData()).isEqualTo(
      listOf(
        Data(id = "1"),
        Data(id = "2")
      )
    )

    val relationships2 = Relationships()
    val data2 = Data(relationships = relationships2)
    assertThat(data2.getGroupedData().isEmpty()).isTrue()

    val data3 = Data()
    assertThat(data3.getGroupedData().isEmpty()).isTrue()
  }

  @Test
  fun getGroupedDataIds() {
    val relationships = Relationships(
      contents = Contents(
        datum = listOf(
          Data(id = "1"),
          Data(id = "2")
        )
      )
    )
    val data = Data(relationships = relationships)
    assertThat(data.getGroupedDataIds()).isEqualTo(
      listOf(
        "1",
        "2"
      )
    )

    val relationships2 = Relationships()
    val data2 = Data(relationships = relationships2)
    assertThat(data2.getGroupedData().isEmpty()).isTrue()

    val data3 = Data()
    assertThat(data3.getGroupedData().isEmpty()).isTrue()
  }

  @Test
  fun toggleFinished() {
    val data = Data(attributes = Attributes(finished = false))
    val result = data.toggleFinished()
    assertThat(result.isFinished()).isTrue()

    val data2 = Data(attributes = Attributes(finished = true))
    val result2 = data2.toggleFinished()
    assertThat(result2.isFinished()).isFalse()
  }

  @Test
  fun getEpisodeNumber() {
    val data = Data(attributes = Attributes(finished = true))
    val result = data.getEpisodeNumber(1, true)
    assertThat(result).isEmpty()

    val data2 = Data(attributes = Attributes(finished = true))
    val result2 = data2.getEpisodeNumber(1, false)
    assertThat(result2).isEmpty()

    val data3 = Data(attributes = Attributes(finished = false))
    val result3 = data3.getEpisodeNumber(1, true)
    assertThat(result3).isEmpty()

    val data4 = Data(attributes = Attributes(finished = false))
    val result4 = data4.getEpisodeNumber(1, false)
    assertThat(result4).isEqualTo("1")
  }

  @Test
  fun getContentId() {
    val data = Data(
      relationships = Relationships(
        content = Content(datum = Data(id = "1"))
      )
    )
    assertThat(data.getContentId()).isEqualTo("1")
  }

  @Test
  fun getDomainIdsFromRelationships() {
    val data = Data(
      relationships = Relationships(
        domains = Contents(
          datum = listOf(
            Data(id = "1"),
            Data(id = "2")
          )
        )
      )
    )
    assertThat(data.getDomainIds()).isEqualTo(listOf("1", "2"))

    val data2 = Data()
    assertThat(data2.getDomainIds()).isEmpty()
  }

  @Test
  fun getDomainIds() {
    val listOfData = listOf(
      Data(id = "1", type = "domains"),
      Data(id = "2", type = "domains"),
      Data(id = "3", type = "progressions")
    )

    assertThat(Data.getDomainIds(listOfData)).isEqualTo(listOf("1", "2"))
  }

  @Test
  fun getCategoryIds() {
    val listOfData = listOf(
      Data(id = "3", type = "categories"),
      Data(id = "4", type = "categories"),
      Data(id = "5", type = "domains")
    )

    assertThat(Data.getCategoryIds(listOfData)).isEqualTo(listOf("3", "4"))
  }

  @Test
  fun getSearchTerm() {
    val filters = listOf(
      Data(
        type = DataType.Search.toRequestFormat(),
        attributes = Attributes(name = "Emitron")
      )
    )
    val searchTerm = Data.getSearchTerm(filters)

    searchTerm isEqualTo "Emitron"
  }

  @Test
  fun getSortOrder() {
    val filters = listOf(
      Data(
        type = DataType.Sort.toRequestFormat(),
        attributes = Attributes(name = "popularity")
      )
    )
    val sortOrder = Data.getSortOrder(filters)

    sortOrder isEqualTo "popularity"

    val filters2 = listOf(
      Data(
        type = DataType.Sort.toRequestFormat(),
        attributes = Attributes(name = "newest")
      )
    )
    val sortOrder2 = Data.getSortOrder(filters2)

    sortOrder2 isEqualTo "-released_at"

    val filters3 = emptyList<Data>()
    val sortOrder3 = Data.getSortOrder(filters3)

    sortOrder3 isEqualTo "-released_at"
  }

  @Test
  fun fromCategory() {
    val expected = Data(
      id = "1",
      type = DataType.Categories.toRequestFormat(),
      attributes = Attributes(
        name = "Architecture"
      )
    )

    val category = Category(
      categoryId = "1",
      name = "Architecture"
    )
    val result = Data.fromCategory(category)

    result isEqualTo expected
  }

  @Test
  fun fromDomain() {
    val expected = Data(
      id = "2",
      type = DataType.Domains.toRequestFormat(),
      attributes = Attributes(
        name = "iOS and Swift",
        level = null
      )
    )

    val domain = Domain(
      domainId = "2",
      name = "iOS and Swift"
    )
    val result = Data.fromDomain(domain)

    result isEqualTo expected
  }

  @Test
  fun fromSearchQuery() {
    val expected = Data(
      type = DataType.Search.toRequestFormat(),
      attributes = Attributes(
        name = "Emitron"
      )
    )

    val result = Data.fromSearchQuery("Emitron")

    result isEqualTo expected
  }

  @Test
  fun fromSortOrder() {
    val expected = Data(
      type = DataType.Sort.toRequestFormat(),
      attributes = Attributes(
        name = "popularity"
      )
    )

    val result = Data.fromSortOrder("popularity")

    result isEqualTo expected
  }

  @Test
  fun getVideoId() {
    val attributes = Attributes(uri = "rw://betamax/collections/122")
    val data = Data(attributes = attributes)
    data.getVideoId() isEqualTo "122"
  }

  @Test
  fun getVideoPlaybackToken() {
    val attributes = Attributes(videoPlaybackToken = "RickAndMorty")
    val data = Data(attributes = attributes)
    data.getVideoPlaybackToken() isEqualTo "RickAndMorty"
  }

  @Test
  fun getStreamUrl() {
    val attributes = Attributes(url = "WubbaLubbaDubDub")
    val data = Data(attributes = attributes)
    data.getStreamUrl() isEqualTo "WubbaLubbaDubDub"
  }

  @Test
  fun setVideoUrl() {
    val dataWithUrl = Data(attributes = Attributes(url = "WubbaLubbaDubDub"))
    val data = Data(id = "1", attributes = Attributes())

    val result = data.setVideoUrl(dataWithUrl)

    result?.getStreamUrl() isEqualTo "WubbaLubbaDubDub"
  }
}
