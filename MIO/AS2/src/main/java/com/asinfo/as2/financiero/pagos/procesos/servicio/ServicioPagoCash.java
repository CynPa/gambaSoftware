package com.asinfo.as2.financiero.pagos.procesos.servicio;

import com.asinfo.as2.entities.DetalleOrdenPagoProveedor;
import com.asinfo.as2.entities.DetallePagoCash;
import com.asinfo.as2.entities.Documento;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.PagoCash;
import com.asinfo.as2.entities.PersonaResponsable;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPagoCash
{
  public abstract void guardar(PagoCash paramPagoCash)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(PagoCash paramPagoCash);
  
  public abstract PagoCash buscarPorId(int paramInt);
  
  public abstract List<PagoCash> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PagoCash> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract PagoCash cargarDetalle(int paramInt);
  
  public abstract PagoCash cargarDetalleAnulacion(int paramInt);
  
  public abstract void cargarFacturasPendientes(PagoCash paramPagoCash);
  
  public abstract void actualizarEstadoPagoCash(int paramInt, Estado paramEstado);
  
  public abstract void contabilizarPagoCash(PagoCash paramPagoCash, String paramString)
    throws ExcepcionAS2;
  
  public abstract void contabilizarPagoCash(PagoCash paramPagoCash, String paramString1, String paramString2)
    throws ExcepcionAS2;
  
  public abstract void contabilizarPagoCashEmpleado(PagoCash paramPagoCash, String paramString)
    throws ExcepcionAS2;
  
  public abstract void actualizaIndicadorBloqueado(int paramInt, boolean paramBoolean)
    throws ExcepcionAS2Financiero;
  
  public abstract void anularPagoCash(PagoCash paramPagoCash)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void esEditable(PagoCash paramPagoCash)
    throws ExcepcionAS2Financiero;
  
  public abstract PagoCash cargarDetalleEmpleado(int paramInt)
    throws ExcepcionAS2Financiero;
  
  public abstract List<Object[]> getDatosArchivoPagoCashEmpleado(PagoCash paramPagoCash, int paramInt);
  
  public abstract void anularPagoCashEmpleado(PagoCash paramPagoCash)
    throws ExcepcionAS2;
  
  public abstract List<Object[]> datosPagoCash(int paramInt, PagoCash paramPagoCash);
  
  public abstract void cargarFacturasPendientes(PagoCash paramPagoCash, Documento paramDocumento, boolean paramBoolean);
  
  public abstract void agregarAnticipo(PagoCash paramPagoCash, DetalleOrdenPagoProveedor paramDetalleOrdenPagoProveedor, Empresa paramEmpresa, BigDecimal paramBigDecimal, String paramString, PersonaResponsable paramPersonaResponsable);
  
  public abstract void datosBanco(DetallePagoCash paramDetallePagoCash);
  
  public abstract List<Object[]> datosPagoCashEmpleado(PagoCash paramPagoCash);
  
  public abstract void actualizarAtributoEntidad(PagoCash paramPagoCash, HashMap<String, Object> paramHashMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPagoCash
 * JD-Core Version:    0.7.0.1
 */