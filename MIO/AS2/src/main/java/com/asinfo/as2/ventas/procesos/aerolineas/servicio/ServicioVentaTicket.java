package com.asinfo.as2.ventas.procesos.aerolineas.servicio;

import com.asinfo.as2.entities.Asiento;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.entities.aerolineas.CargaArchivo;
import com.asinfo.as2.entities.aerolineas.Ticket;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioVentaTicket
{
  public abstract void guardar(CargaArchivo paramCargaArchivo)
    throws AS2Exception;
  
  public abstract List<CargaArchivo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CargaArchivo cargarDetalle(int paramInt);
  
  public abstract List<CargaArchivo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void facturarLote(CargaArchivo paramCargaArchivo, PuntoDeVenta paramPuntoDeVenta, Empresa paramEmpresa)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract List<Ticket> obtenerListaTicketPorFechas(List<Asiento> paramList, Date paramDate1, Date paramDate2, int paramInt, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract Asiento contabilizar(List<Ticket> paramList, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(CargaArchivo paramCargaArchivo);
  
  public abstract List<Asiento> listaAsientos(List<Ticket> paramList);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.aerolineas.servicio.ServicioVentaTicket
 * JD-Core Version:    0.7.0.1
 */