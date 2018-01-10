package com.asinfo.as2.financiero.contabilidad.procesos.servicio;

import com.asinfo.as2.entities.Asiento;
import com.asinfo.as2.entities.CajaChica;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCajaChica
{
  public abstract void guardar(CajaChica paramCajaChica);
  
  public abstract void eliminar(CajaChica paramCajaChica)
    throws ExcepcionAS2Financiero;
  
  public abstract CajaChica buscarPorId(Integer paramInteger);
  
  public abstract void contabilizar(CajaChica paramCajaChica, Asiento paramAsiento)
    throws ExcepcionAS2;
  
  public abstract List<CajaChica> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract CajaChica cargarDetalle(int paramInt);
  
  public abstract List<CajaChica> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void esEditable(CajaChica paramCajaChica)
    throws ExcepcionAS2Financiero;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Asiento vistaPreviaAsiento(CajaChica paramCajaChica, int paramInt)
    throws ExcepcionAS2;
  
  public abstract void verificaEmisionRetencion(CajaChica paramCajaChica)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCajaChica
 * JD-Core Version:    0.7.0.1
 */