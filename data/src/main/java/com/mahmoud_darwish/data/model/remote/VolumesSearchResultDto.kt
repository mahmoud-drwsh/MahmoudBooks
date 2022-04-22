package com.mahmoud_darwish.data.model.remote

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class VolumesSearchResultDto(
    @SerializedName("items")
    val volumeDtos: List<VolumeDto> = listOf(),
    @SerializedName("kind")
    val kind: String = "",
    @SerializedName("totalItems")
    val totalItems: Int = 0
) {
    @Entity
    data class VolumeDto(
        @SerializedName("accessInfoDto")
        val accessInfoDto: AccessInfoDto = AccessInfoDto(),
        @SerializedName("etag")
        val etag: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("kind")
        val kind: String = "",
        @SerializedName("saleInfoDto")
        val saleInfoDto: SaleInfoDto = SaleInfoDto(),
        @SerializedName("searchInfoDto")
        val searchInfoDto: SearchInfoDto = SearchInfoDto(),
        @SerializedName("selfLink")
        val selfLink: String = "",
        @SerializedName("volumeInfoDto")
        val volumeInfoDto: VolumeInfoDto = VolumeInfoDto()
    ) {
        @Entity
        data class AccessInfoDto(
            @SerializedName("accessViewStatus")
            val accessViewStatus: String = "",
            @SerializedName("country")
            val country: String = "",
            @SerializedName("embeddable")
            val embeddable: Boolean = false,
            @SerializedName("epub")
            val epub: Epub = Epub(),
            @SerializedName("pdf")
            val pdf: Pdf = Pdf(),
            @SerializedName("publicDomain")
            val publicDomain: Boolean = false,
            @SerializedName("quoteSharingAllowed")
            val quoteSharingAllowed: Boolean = false,
            @SerializedName("textToSpeechPermission")
            val textToSpeechPermission: String = "",
            @SerializedName("viewability")
            val viewability: String = "",
            @SerializedName("webReaderLink")
            val webReaderLink: String = ""
        ) {
            @Entity
            data class Epub(
                @SerializedName("acsTokenLink")
                val acsTokenLink: String = "",
                @SerializedName("isAvailable")
                val isAvailable: Boolean = false
            )

            @Entity
            data class Pdf(
                @SerializedName("acsTokenLink")
                val acsTokenLink: String = "",
                @SerializedName("isAvailable")
                val isAvailable: Boolean = false
            )
        }

        @Entity
        data class SaleInfoDto(
            @SerializedName("buyLink")
            val buyLink: String = "",
            @SerializedName("country")
            val country: String = "",
            @SerializedName("isEbook")
            val isEbook: Boolean = false,
            @SerializedName("listPrice")
            val listPrice: ListPrice = ListPrice(),
            @SerializedName("offers")
            val offers: List<Offer> = listOf(),
            @SerializedName("retailPrice")
            val retailPrice: RetailPrice = RetailPrice(),
            @SerializedName("saleability")
            val saleability: String = ""
        ) {
            @Entity
            data class ListPrice(
                @SerializedName("amount")
                val amount: Int = 0,
                @SerializedName("currencyCode")
                val currencyCode: String = ""
            )

            @Entity
            data class Offer(
                @SerializedName("finskyOfferType")
                val finskyOfferType: Int = 0,
                @SerializedName("listPrice")
                val listPrice: ListPrice = ListPrice(),
                @SerializedName("retailPrice")
                val retailPrice: RetailPrice = RetailPrice()
            ) {
                @Entity
                data class ListPrice(
                    @SerializedName("amountInMicros")
                    val amountInMicros: Long = 0,
                    @SerializedName("currencyCode")
                    val currencyCode: String = ""
                )

                @Entity
                data class RetailPrice(
                    @SerializedName("amountInMicros")
                    val amountInMicros: Long = 0,
                    @SerializedName("currencyCode")
                    val currencyCode: String = ""
                )
            }

            @Entity
            data class RetailPrice(
                @SerializedName("amount")
                val amount: Int = 0,
                @SerializedName("currencyCode")
                val currencyCode: String = ""
            )
        }

        @Entity
        data class SearchInfoDto(
            @SerializedName("textSnippet")
            val textSnippet: String = ""
        )

        @Entity
        data class VolumeInfoDto(
            @SerializedName("allowAnonLogging")
            val allowAnonLogging: Boolean = false,
            @SerializedName("authors")
            val authors: List<String> = listOf(),
            @SerializedName("canonicalVolumeLink")
            val canonicalVolumeLink: String = "",
            @SerializedName("categories")
            val categories: List<String> = listOf(),
            @SerializedName("contentVersion")
            val contentVersion: String = "",
            @SerializedName("description")
            val description: String = "",
            @SerializedName("imageLinks")
            val imageLinks: ImageLinks = ImageLinks(),
            @SerializedName("industryIdentifiers")
            val industryIdentifiers: List<IndustryIdentifier> = listOf(),
            @SerializedName("infoLink")
            val infoLink: String = "",
            @SerializedName("language")
            val language: String = "",
            @SerializedName("maturityRating")
            val maturityRating: String = "",
            @SerializedName("pageCount")
            val pageCount: Int = 0,
            @SerializedName("panelizationSummary")
            val panelizationSummary: PanelizationSummary = PanelizationSummary(),
            @SerializedName("previewLink")
            val previewLink: String = "",
            @SerializedName("printType")
            val printType: String = "",
            @SerializedName("publishedDate")
            val publishedDate: String = "",
            @SerializedName("publisher")
            val publisher: String = "",
            @SerializedName("readingModes")
            val readingModes: ReadingModes = ReadingModes(),
            @SerializedName("subtitle")
            val subtitle: String = "",
            @SerializedName("title")
            val title: String = ""
        ) {
            @Entity
            data class ImageLinks(
                @SerializedName("smallThumbnail")
                val smallThumbnail: String = "",
                @SerializedName("thumbnail")
                val thumbnail: String = ""
            )

            @Entity
            data class IndustryIdentifier(
                @SerializedName("identifier")
                val identifier: String = "",
                @SerializedName("type")
                val type: String = ""
            )

            @Entity
            data class PanelizationSummary(
                @SerializedName("containsEpubBubbles")
                val containsEpubBubbles: Boolean = false,
                @SerializedName("containsImageBubbles")
                val containsImageBubbles: Boolean = false
            )

            @Entity
            data class ReadingModes(
                @SerializedName("image")
                val image: Boolean = false,
                @SerializedName("text")
                val text: Boolean = false
            )
        }
    }
}