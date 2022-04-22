package com.mahmoud_darwish.data.model.local

import androidx.room.Entity

@Entity
data class VolumesSearchResultDto(
    val volumeDtos: List<VolumeDto> = listOf(),
    val kind: String = "",
    val totalItems: Int = 0
) {
    @Entity
    data class VolumeDto(
        val accessInfoDto: AccessInfoDto = AccessInfoDto(),
        val etag: String = "",
        val id: String = "",
        val kind: String = "",
        val saleInfoDto: SaleInfoDto = SaleInfoDto(),
        val searchInfoDto: SearchInfoDto = SearchInfoDto(),
        val selfLink: String = "",
        val volumeInfoDto: VolumeInfoDto = VolumeInfoDto()
    ) {
        @Entity
        data class AccessInfoDto(
            val accessViewStatus: String = "",
            val country: String = "",
            val embeddable: Boolean = false,
            val epub: Epub = Epub(),
            val pdf: Pdf = Pdf(),
            val publicDomain: Boolean = false,
            val quoteSharingAllowed: Boolean = false,
            val textToSpeechPermission: String = "",
            val viewability: String = "",
            val webReaderLink: String = ""
        ) {
            @Entity
            data class Epub(
                val acsTokenLink: String = "",
                val isAvailable: Boolean = false
            )

            @Entity
            data class Pdf(
                val acsTokenLink: String = "",
                val isAvailable: Boolean = false
            )
        }

        @Entity
        data class SaleInfoDto(
            val buyLink: String = "",
            val country: String = "",
            val isEbook: Boolean = false,
            val listPrice: ListPrice = ListPrice(),
            val offers: List<Offer> = listOf(),
            val retailPrice: RetailPrice = RetailPrice(),
            val saleability: String = ""
        ) {
            @Entity
            data class ListPrice(
                val amount: Int = 0,
                val currencyCode: String = ""
            )

            @Entity
            data class Offer(
                val finskyOfferType: Int = 0,
                val listPrice: ListPrice = ListPrice(),
                val retailPrice: RetailPrice = RetailPrice()
            ) {
                @Entity
                data class ListPrice(
                    val amountInMicros: Long = 0,
                    val currencyCode: String = ""
                )

                @Entity
                data class RetailPrice(
                    val amountInMicros: Long = 0,
                    val currencyCode: String = ""
                )
            }

            @Entity
            data class RetailPrice(
                val amount: Int = 0,
                val currencyCode: String = ""
            )
        }

        @Entity
        data class SearchInfoDto(
            val textSnippet: String = ""
        )

        @Entity
        data class VolumeInfoDto(
            val allowAnonLogging: Boolean = false,
            val authors: List<String> = listOf(),
            val canonicalVolumeLink: String = "",
            val categories: List<String> = listOf(),
            val contentVersion: String = "",
            val description: String = "",
            val imageLinks: ImageLinks = ImageLinks(),
            val industryIdentifiers: List<IndustryIdentifier> = listOf(),
            val infoLink: String = "",
            val language: String = "",
            val maturityRating: String = "",
            val pageCount: Int = 0,
            val panelizationSummary: PanelizationSummary = PanelizationSummary(),
            val previewLink: String = "",
            val printType: String = "",
            val publishedDate: String = "",
            val publisher: String = "",
            val readingModes: ReadingModes = ReadingModes(),
            val subtitle: String = "",
            val title: String = ""
        ) {
            @Entity
            data class ImageLinks(
                val smallThumbnail: String = "",
                val thumbnail: String = ""
            )

            @Entity
            data class IndustryIdentifier(
                val identifier: String = "",
                val type: String = ""
            )

            @Entity
            data class PanelizationSummary(
                val containsEpubBubbles: Boolean = false,
                val containsImageBubbles: Boolean = false
            )

            @Entity
            data class ReadingModes(
                val image: Boolean = false,
                val text: Boolean = false
            )
        }
    }
}