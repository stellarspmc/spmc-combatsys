package tk.spmc.common.weapons

class WeaponList {
    private var weaponList: HashMap<String, HashMap<Int, BaseWeapon>> = HashMap()
    private var assaultRifleList: HashMap<Int, BaseWeapon> = HashMap()

    fun initializeWeapons() {
        assaultRifleList[1] = BaseWeapon(1, "aek", "test")
        weaponList["AR"] = assaultRifleList
    }
}