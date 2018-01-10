package com.asinfo.as2.financiero.contabilidad.reportes.servicio;

import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
import com.asinfo.as2.entities.MovimientoBancario;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteMovimientoBancario
{
  public abstract List<MovimientoBancario> getReporteMovimientoBancario(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, int paramInt4, boolean paramBoolean2, Map<String, String> paramMap, String paramString);
  
  public abstract List getReporteChequesGiradosNoCobrados(Date paramDate1, Date paramDate2, CuentaBancariaOrganizacion paramCuentaBancariaOrganizacion);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteMovimientoBancario
 * JD-Core Version:    0.7.0.1
 */