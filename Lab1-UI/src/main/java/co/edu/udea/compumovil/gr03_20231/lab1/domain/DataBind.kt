package co.edu.udea.compumovil.gr03_20231.lab1.domain

class DataBind(personal: PersonalData, contact: ContactData) {

    var personal: PersonalData = personal
    var contact: ContactData = contact


    override fun toString(): String {
        val sb = StringBuilder()

        sb.append("Información personal:\n")
        sb.append("${personal.name}\n")
        personal.sex?.let { sb.append("$it\n") }
        sb.append("Nació el ${personal.birthday}\n")
        personal.education_level?.let { sb.append("$it\n") }

        sb.append("Información de contacto:\n")
        sb.append("Teléfono: ${contact.phone}\n")
        contact.address?.let { sb.append("Dirección: $it\n") }
        sb.append("Email: ${contact.email}\n")
        sb.append("País: ${contact.country}\n")
        contact.city?.let { sb.append("Ciudad: $it\n") }

        return sb.toString()
    }

}