package com.asinfo.as2.financiero.contabilidad.reportes.servicio;

import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.enumeraciones.EstadoGarantiaCliente;
import com.asinfo.as2.enumeraciones.TipoGarantiaCliente;
import com.asinfo.as2.enumeraciones.TipoOrganizacion;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteGarantiaCliente
{
  public abstract List getReporteGarantiaCliente(int paramInt);
  
  public abstract List getReporteGarantiaClienteLista(Empresa paramEmpresa, EstadoGarantiaCliente paramEstadoGarantiaCliente, Date paramDate1, Date paramDate2, TipoGarantiaCliente paramTipoGarantiaCliente, int paramInt, boolean paramBoolean, Sucursal paramSucursal, TipoOrganizacion paramTipoOrganizacion);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteGarantiaCliente
 * JD-Core Version:    0.7.0.1
 */