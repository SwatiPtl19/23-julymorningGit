package neoStoxTest;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import neoStoxBase.BaseNew;
import neoStoxPOM.NeoStoxHomePage;
import neoStoxPOM.NeoStoxLogin;
import neoStoxPOM.NeoStoxPasswordPage;
import neoStoxUtility.UtilityNew;
@Listeners(neoStoxUtility.Listener.class)

public class ValidateNeoStoxUserNameUsingPropertyFile extends BaseNew
{
	NeoStoxLogin login;
	NeoStoxPasswordPage password;
	NeoStoxHomePage home;
  @BeforeClass
  public void launchNeoStox() throws IOException
  {
	 launchBrowser();
	 login=new NeoStoxLogin(driver);
	 password=new NeoStoxPasswordPage(driver);
	 home=new NeoStoxHomePage(driver);
   }
  @BeforeMethod
  public void loginToNeoStox() throws InterruptedException, IOException
  {
	  login.sendMobileNum(driver,UtilityNew.readDataFromPropertyFile("mobileNum"));
	  login.clickOnSignInButton(driver);
	  Thread.sleep(2000);
	  password.sendPassword(driver,UtilityNew.readDataFromPropertyFile("password"));
	  password.clickOnSubmitButton(driver);
	  Thread.sleep(1000);
	  home.popUpHandle(driver);
  }
  @Test
  public void validateUserName() throws IOException, InterruptedException 
  {  
	  Thread.sleep(1000);
	 Assert.assertEquals(home.getUserName(),UtilityNew.readDataFromPropertyFile("userName"),"TC failed,actual and expected UserNames are not matching");
	 
  }
  @Test
  public void validateACBalance() 
  {
	  
	  Assert.assertNotNull(home.getBalance(driver));
	  Reporter.log("AC Balance is"+home.getBalance(driver),true);
  }
  @AfterMethod
  public void logout()
  {
	  home.logOutFromNeoStox(driver);
  }
  @AfterClass
  public void closeApplication() throws InterruptedException
  {
	  closingBrowser(driver);
  }
}
