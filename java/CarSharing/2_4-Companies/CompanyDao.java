package carsharing;

import java.util.List;

public interface CompanyDao {
    public List<Company> getAllCompanies(String URL);
    public void createCompany(String URL);
    public void addToDatabase(String name, String URL);

}
