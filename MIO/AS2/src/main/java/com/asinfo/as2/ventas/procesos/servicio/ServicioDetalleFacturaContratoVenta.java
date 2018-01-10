package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.DetallesFacturaContratoVenta;
import com.asinfo.as2.entities.Documento;
import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.Organizacion;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDetalleFacturaContratoVenta
{
  public abstract List<DetallesFacturaContratoVenta> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void guardar(DetallesFacturaContratoVenta paramDetallesFacturaContratoVenta)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void crearFacturaCliente(List<DetallesFacturaContratoVenta> paramList, Date paramDate, PuntoDeVenta paramPuntoDeVenta, Sucursal paramSucursal, Organizacion paramOrganizacion, Documento paramDocumento)
    throws ExcepcionAS2;
  
  public abstract void generarValoresDevengadosNotaCredito(FacturaCliente paramFacturaCliente, DetallesFacturaContratoVenta paramDetallesFacturaContratoVenta, BigDecimal paramBigDecimal);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioDetalleFacturaContratoVenta
 * JD-Core Version:    0.7.0.1
 */