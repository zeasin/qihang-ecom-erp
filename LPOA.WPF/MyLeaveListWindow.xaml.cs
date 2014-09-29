
using LPOA.Entity;
using LPOA.Sqlite;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace LPOA.WPF
{
    /// <summary>
    /// MyLeaveListWindow.xaml 的交互逻辑
    /// </summary>
    public partial class MyLeaveListWindow : Window
    {
        public MyLeaveListWindow()
        {
            InitializeComponent();
        }
        string LoginUser = "齐李平";
        private void Grid_Loaded(object sender, RoutedEventArgs e)
        {

        }
        ObservableCollection<LeaveEntity> ls;
        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            
            lblUser.Content = lblUser.Content + LoginUser;
            ls = new ObservableCollection<LeaveEntity>();

            LeavesDAL dal = new LeavesDAL();
            var list = dal.ExecuteListByUser(LoginUser);// .Leaves.Where(c => c.LeaveUser == LoginUser).OrderBy(c => c.Id).ToList();
            foreach (var item in list)
            {
                ls.Add(item);
            }

            //for (int i = 0; i < 10; i++)
            //{
            //    ls.Add(new Model.Leave() { Title = "t" + i, LeaveType = "事假" + i, LeaveUser = "AA" + i, StartDate = DateTime.Now });

            //}

            dgLeaves.ItemsSource = ls; 
        }

        private void DataGrid_PreviewMouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            var obj = sender as DataGrid;
            LeaveEntity le = obj.SelectedItem as LeaveEntity;
            LeaveDetailWindow win = new LeaveDetailWindow();
            win.LeaveId = le.Id;
            win.LoginUser = LoginUser;
            win.ShowDialog();
            //MessageBox.Show(le.Title);
        }
    }
}
