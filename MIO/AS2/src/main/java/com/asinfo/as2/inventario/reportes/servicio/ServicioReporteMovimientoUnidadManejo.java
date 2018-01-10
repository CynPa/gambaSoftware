package com.asinfo.as2.inventario.reportes.servicio;

import com.asinfo.as2.entities.DetalleMovimientoUnidadManejo;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.MovimientoUnidadManejo;
import com.asinfo.as2.entities.SaldoUnidadManejo;
import com.asinfo.as2.entities.Subempresa;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.Transportista;
import com.asinfo.as2.entities.UnidadManejo;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteMovimientoUnidadManejo
{
  public abstract List<SaldoUnidadManejo> getSaldoUnidadManejoSucursal(int paramInt, Sucursal paramSucursal);
  
  public abstract List<SaldoUnidadManejo> getSaldoUnidadManejoTransportista(int paramInt, Transportista paramTransportista);
  
  public abstract List<SaldoUnidadManejo> getSaldoUnidadManejoEmpresa(int paramInt, Empresa paramEmpresa, Subempresa paramSubempresa);
  
  public abstract List<Object[]> getSalgoInicialUnidadManejo(int paramInt, Sucursal paramSucursal, Transportista paramTransportista, Empresa paramEmpresa, Subempresa paramSubempresa, UnidadManejo paramUnidadManejo, Date paramDate1, Date paramDate2);
  
  public abstract List<DetalleMovimientoUnidadManejo> getReporteKardexUnidadManejo(int paramInt, Sucursal paramSucursal, Transportista paramTransportista, Empresa paramEmpresa, Subempresa paramSubempresa, UnidadManejo paramUnidadManejo, Date paramDate1, Date paramDate2);
  
  public abstract List<Object[]> getReporteTransferencia(MovimientoUnidadManejo paramMovimientoUnidadManejo);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoUnidadManejo
 * JD-Core Version:    0.7.0.1
 */