package NetworkMessage;

public enum eFunctions
{
	//chat
	GetAllConnectedWorkes,
	NewConnectedUser,
	RemoveConnectedUser,
	SendMessage,
	
	//user
	SignIn,
	SignOut,
	
	//adding
	AddWorker,
	AddCustomer,
	
	//removing
	RemoveWorker,
	RemoveCustomer,
	
	//item
	GetAllExistItems,
	GetItemsAmount,
	IncItemAmount,
	
	//purchase
	GetAllPurchaseTrack,
	GetCustomerByID,
	ChangePurchaseTrack,
	CommitPurchase,
	GetPurchaseTotalPrice,
	
	//reports
	GetVIPCustomers,
	GetAllCustomers,
	GetAllWorkers,
	GetTotaySalesReport,
	GetAllLogs,
	
	CloseConnection;
}
