package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.CategoriaEmpresa;
import com.asinfo.as2.entities.DetalleDocumentoDigitalizado;
import com.asinfo.as2.entities.DocumentoDigitalizado;
import com.asinfo.as2.entities.Empresa;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDetalleDocumentoDigitalizado
{
  public abstract List<DetalleDocumentoDigitalizado> cargarListaDetalleDocumentoDigitalizadoEmpleado(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void guardar(DetalleDocumentoDigitalizado paramDetalleDocumentoDigitalizado);
  
  public abstract List reporteDocumentosDigitalizadosPorEmpleado(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract List reporteDocumentosDigitalizados(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, Date paramDate);
  
  public abstract List<DetalleDocumentoDigitalizado> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<DetalleDocumentoDigitalizado> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<Object[]> reporteDocumentosDigitalizadosEmpresa(int paramInt, Empresa paramEmpresa, CategoriaEmpresa paramCategoriaEmpresa, boolean paramBoolean1, boolean paramBoolean2, DocumentoDigitalizado paramDocumentoDigitalizado, boolean paramBoolean3, boolean paramBoolean4, Date paramDate);
  
  public abstract List<DetalleDocumentoDigitalizado> cargarListaDetalleDocumentoDigitalizadoEmpresa(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, int paramInt4);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioDetalleDocumentoDigitalizado
 * JD-Core Version:    0.7.0.1
 */