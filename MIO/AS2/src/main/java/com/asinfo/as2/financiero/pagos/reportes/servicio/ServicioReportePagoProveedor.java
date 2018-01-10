package com.asinfo.as2.financiero.pagos.reportes.servicio;

import com.asinfo.as2.entities.CategoriaEmpresa;
import com.asinfo.as2.entities.DetalleFormaPago;
import com.asinfo.as2.entities.PagoCash;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReportePagoProveedor
{
  public abstract List getReportePagoProveedor(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, CategoriaEmpresa paramCategoriaEmpresa)
    throws ExcepcionAS2;
  
  public abstract List getReportePago(int paramInt);
  
  public abstract List<Object[]> getCashManagementProveedor(PagoCash paramPagoCash)
    throws AS2Exception;
  
  public abstract Object[] getCuentaBancariaProveedor(Integer paramInteger);
  
  public abstract List<DetalleFormaPago> getDetalleFormaCobro(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.reportes.servicio.ServicioReportePagoProveedor
 * JD-Core Version:    0.7.0.1
 */