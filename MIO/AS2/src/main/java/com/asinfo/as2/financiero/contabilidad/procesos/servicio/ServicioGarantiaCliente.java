package com.asinfo.as2.financiero.contabilidad.procesos.servicio;

import com.asinfo.as2.entities.GarantiaCliente;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioGarantiaCliente
{
  public abstract void guardar(GarantiaCliente paramGarantiaCliente)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void eliminar(GarantiaCliente paramGarantiaCliente);
  
  public abstract GarantiaCliente buscarPorId(Integer paramInteger);
  
  public abstract List<GarantiaCliente> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<GarantiaCliente> buscaListaChequesPosfechados(int paramInt);
  
  public abstract BigDecimal obtenerSaldoChequePosfechado(int paramInt, Date paramDate);
  
  public abstract List<GarantiaCliente> getListaGarantiaClienteCobro(int paramInt);
  
  public abstract GarantiaCliente cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioGarantiaCliente
 * JD-Core Version:    0.7.0.1
 */