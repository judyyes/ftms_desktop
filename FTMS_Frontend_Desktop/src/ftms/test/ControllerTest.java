package ftms.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ftms.controller.*;
import ftms.model.*;

public class ControllerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLogin() throws Exception {
		FTMSController loginTest = new FTMSController();
		// login successfully 
		try {
			loginTest.login("admin", "147/258/369");
		}catch (InvalidInputException e){
			fail();
		}
		assertEquals("x22ykbi0mkkj5qqporji8pgyvwjepgqi", loginTest.getUserID());
		assertEquals("Manager", loginTest.getPosition());
		
		// wrong password
		try {
			loginTest.login("admin", "adminn");
		} catch(InvalidInputException e) {
			assertEquals("WRONG PASSWORD", e.getMessage());
		}
		
		// non-existing user name
		try {
			loginTest.login("cook", "admin");
		} catch(InvalidInputException e) {
			assertEquals("NO SUCH USER", e.getMessage());
		}
	}
	
	@Test
	public void testShowProfile() {
		FTMSController showProfileTest = new FTMSController();
		// Show Profile successfully
		String name = "";
		String gender = "";
		long phone = 0;
		try {
			showProfileTest.login("admin", "147/258/369");
			Staff staff = showProfileTest.showProfile();
			name = staff.getNAME();
			gender = staff.getGENDER();
			phone = staff.getTEL();
		} catch (Exception e) {
			fail();
		}
		assertEquals("shawn", name);
		assertEquals("male", gender);
		assertEquals(1234567890, phone);
	}
	
	@Test
	public void testShowFood() {
		FTMSController showFoodTest = new FTMSController();
		List<Food> foods = null;
		// Show food successfully
		try {
			showFoodTest.login("admin", "147/258/369");
			showFoodTest.showFood();
			Manager m = Manager.getInstance();
			foods = m.getFoods();
		} catch (Exception e) {
			fail();
		}
		assertEquals("apple", foods.get(0).getNAME());
		assertEquals(1, foods.get(0).getQUANTITY());
	}
	
	@Test
	public void testCreateFood() throws Exception {
		FTMSController createFoodTest = new FTMSController();
		List<Food> foods = null;
		createFoodTest.login("admin", "147/258/369");
		// create food successfully
		
		try {
			createFoodTest.createFood("shrimp", 1, 3);
			Manager m = Manager.getInstance();
			foods = m.getFoods();
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals("shrimp", foods.get(foods.size()-1).getNAME());
		assertEquals(1,foods.get(foods.size()-1).getQUANTITY());
		
		// food name is empty
		try {
			createFoodTest.createFood("", 1, 3);
		} catch (InvalidInputException e) {
			assertEquals("Food name cannot be empty!", e.getMessage());
		}
		
		// quantity is negative
		try {
			createFoodTest.createFood("coke", (-1), 3);
		} catch (InvalidInputException e) {
			assertEquals("Food quantity cannot be negative!", e.getMessage());
		}
		
		// price is less than 0
		try {
			createFoodTest.createFood("coke", 1, -5);
		} catch (InvalidInputException e) {
			assertEquals("Food price cannot be less than 0!", e.getMessage());
		}
		
	}
	
	@Test
	public void testRemoveFood() throws Exception {
		FTMSController removeFoodTest = new FTMSController();
		List<Food> foods = null;
		Manager m = Manager.getInstance();
		foods = m.getFoods();
		removeFoodTest.login("admin", "147/258/369");
		Food shrimp = new Food("shrimp", 1, 3);
		
		// remove food successfully
		try {
			removeFoodTest.removeFood(shrimp);
		} catch (InvalidInputException e) {
			fail();
		}
		
		// remove food failed
		try {
			removeFoodTest.removeFood(shrimp);
		} catch (InvalidInputException e) {
			assertEquals("404: Item Not Found", e.getMessage());
		}
	}
	
	@Test
	public void testShowEquipment() throws InvalidInputException, Exception {
		FTMSController showEquipmentTest = new FTMSController();
		showEquipmentTest.login("admin", "147/258/369");
		List<Equipment> equipments = null;
		
		// Show Equipment successfully
		try {
			showEquipmentTest.showEquipment();
			Manager m = Manager.getInstance();
			equipments = m.getEquipments();
		} catch (Exception e) {
			fail();
		}
		assertEquals("chopstick", equipments.get(0).getNAME());
		assertEquals(100, equipments.get(0).getQUANTITY());
	}

	
	@Test
	public void testCreateEquipment() throws Exception {
		FTMSController createEquipmentTest = new FTMSController();
		List<Equipment> equipments = null;
		createEquipmentTest.login("admin", "147/258/369");
		
		// create Equipment successfully
		try {
			createEquipmentTest.createEquipment("plate", 10, 1);
			Manager m = Manager.getInstance();
			equipments = m.getEquipments();
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals("plate", equipments.get(equipments.size()-1).getNAME());
		assertEquals(10,equipments.get(equipments.size()-1).getQUANTITY());
		
		// equipment name is empty
		try {
			createEquipmentTest.createEquipment("", 10, 1);
		} catch (InvalidInputException e) {
			assertEquals("Equipment name cannot be empty!", e.getMessage());
		}
		
		// quantity is negative
		try {
			createEquipmentTest.createEquipment("plate", -1, 3);
		} catch (InvalidInputException e) {
			assertEquals("Equipment quantity cannot be negative!", e.getMessage());
		}
		
		// price is less than 0
		try {
			createEquipmentTest.createEquipment("plate", 1, -5);
		} catch (InvalidInputException e) {
			assertEquals("Equipment price cannot be less than 0!", e.getMessage());
		}
		
		
	}
	@Test
	public void testRemoveEquipment() throws Exception {
		FTMSController removeEquipmentTest = new FTMSController();
		List<Equipment> equipments = null;
		Manager m = Manager.getInstance();
		equipments = m.getEquipments();
		removeEquipmentTest.login("admin", "147/258/369");
		Equipment plate = new Equipment("plate", 10, 1);
		
		// remove food successfully
		try {
			removeEquipmentTest.removeEquipment(plate);
		} catch (InvalidInputException e) {
			fail();
		}
		
		// remove food failed
		try {
			removeEquipmentTest.removeEquipment(plate);
		} catch (InvalidInputException e) {
			assertEquals("404: Item Not Found", e.getMessage());
		}
	}
	
	@Test
	// Test displaying menu
	public void testShowMenu() throws InvalidInputException, Exception {
		FTMSController showMenuTest = new FTMSController();
		showMenuTest.login("admin", "147/258/369");
		List<Menu> menu = null;
		
		// Show Menu successfully
		try {
			showMenuTest.showMenu();
			Manager m = Manager.getInstance();
			menu = m.getMenus();
		} catch (Exception e) {
			fail();
		}
		assertEquals("hamburger", menu.get(0).getNAME());
		assertEquals(6, menu.get(0).getPOPULARITY());
	}
	
	@Test
	// Test creating menu
	public void testCreateMenu() throws Exception {
		FTMSController createMenuTest = new FTMSController();
		List<Menu> menu = null;
		createMenuTest.login("admin", "147/258/369");
		
		// create Menu successfully
		try {
			createMenuTest.createMenu("seafood salad", 3.99f, 0);
			Manager m = Manager.getInstance();
			menu = m.getMenus();
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals("seafood salad", menu.get(menu.size()-1).getNAME());
		assertEquals(0,menu.get(menu.size()-1).getPOPULARITY());
		
		// menu name is empty
		try {
			createMenuTest.createMenu("", 3.99f, 0);
		} catch (InvalidInputException e) {
			assertEquals("Menu name cannot be empty!", e.getMessage());
		}
		
		// price is less than 0
		try {
			createMenuTest.createMenu("seafood salad", -3.99f, 0);
		} catch (InvalidInputException e) {
			assertEquals("Menu price cannot be less than 0!", e.getMessage());
		}
	}
	
	@Test
	public void testRemoveMenu() throws Exception {
		FTMSController removeMenuTest = new FTMSController();
		List<Menu> menu = null;
		Manager m = Manager.getInstance();
		menu = m.getMenus();
		removeMenuTest.login("admin", "147/258/369");
		Menu seafoodSalad = menu.get(menu.size()-1);
		
		// remove food successfully
		try {
			removeMenuTest.removeMenu(seafoodSalad);
		} catch (InvalidInputException e) {
			fail();
		}
		
		// remove food failed
		try {
			removeMenuTest.removeMenu(seafoodSalad);
		} catch (InvalidInputException e) {
			assertEquals("404: Item Not Found", e.getMessage());
		}
	}
	
	@Test
	// Test displaying menu
	public void testShowOrder() throws InvalidInputException, Exception {
		FTMSController showOrderTest = new FTMSController();
		showOrderTest.login("admin", "147/258/369");
		List<Order> order = null;
		
		// Show Menu successfully
		try {
			showOrderTest.showOrder();
			Manager m = Manager.getInstance();
			order = m.getOrders();
		} catch (Exception e) {
			fail();
		}
		assertEquals("1", order.get(0).getSTATUS());
	}
}
