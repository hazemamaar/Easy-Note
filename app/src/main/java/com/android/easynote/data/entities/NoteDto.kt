package com.android.easynote.data.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class NoteDto(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "date_time")
    var dateTime: String? = null,

    @ColumnInfo(name = "note_text")
    var noteText: String? = null,

    @ColumnInfo(name = "pin")
    var pin:Int =0,

    @ColumnInfo(name = "lock")
    var lock:String ?=null,

    @ColumnInfo(name = "color")
    var color: String? = null,
    @ColumnInfo(name = "image_path")
    var imgPath: String? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(dateTime)
        parcel.writeString(noteText)
        parcel.writeInt(pin)
        parcel.writeString(lock)
        parcel.writeString(color)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteDto> {
        override fun createFromParcel(parcel: Parcel): NoteDto {
            return NoteDto(parcel)
        }

        override fun newArray(size: Int): Array<NoteDto?> {
            return arrayOfNulls(size)
        }
    }


}
