package com.asinfo.as2.compras.reportes.servicio;

import com.asinfo.as2.entities.DimensionContable;
import com.asinfo.as2.entities.Documento;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.enumeraciones.Estado;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReportePedidoProveedor
{
  public abstract List<Object[]> getReportePedidoProveedor(int paramInt);
  
  public abstract List<Object[]> getListaReportePedidoProveedor(int paramInt, Date paramDate1, Date paramDate2, String paramString1, String paramString2, Empresa paramEmpresa, Documento paramDocumento, boolean paramBoolean1, boolean paramBoolean2, DimensionContable paramDimensionContable, Integer paramInteger, Estado paramEstado);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.servicio.ServicioReportePedidoProveedor
 * JD-Core Version:    0.7.0.1
 */