/*   1:    */ package com.asinfo.as2.rs.inventario.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   9:    */ public class CrearDevolucionRequestDto
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private Integer usuario;
/*  13:    */   private Integer organizacion;
/*  14:    */   private Integer facturaPadre;
/*  15:    */   private Integer sucursal;
/*  16:    */   private Integer puntoVenta;
/*  17:    */   private String fecha;
/*  18:    */   private Integer cliente;
/*  19:    */   private Integer subcliente;
/*  20:    */   private String nota;
/*  21:    */   private String codigoMovil;
/*  22:    */   private String urlApp;
/*  23:    */   private Integer motivoNotaCreditoCliente;
/*  24:    */   private BigDecimal total;
/*  25:    */   private Integer idDispositivoSincronizacion;
/*  26:    */   private String numeroAs2;
/*  27:    */   private String descripcion;
/*  28:    */   private String referencia8;
/*  29:    */   private Integer idAs2;
/*  30:    */   private List<DetalleDevolucionRequestDto> listaDetalleDevolucion;
/*  31:    */   private Boolean indicadorDevolucion;
/*  32:    */   
/*  33:    */   public Integer getUsuario()
/*  34:    */   {
/*  35: 57 */     return this.usuario;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setUsuario(Integer usuario)
/*  39:    */   {
/*  40: 65 */     this.usuario = usuario;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Integer getOrganizacion()
/*  44:    */   {
/*  45: 72 */     return this.organizacion;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setOrganizacion(Integer organizacion)
/*  49:    */   {
/*  50: 80 */     this.organizacion = organizacion;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Integer getSucursal()
/*  54:    */   {
/*  55: 87 */     return this.sucursal;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setSucursal(Integer sucursal)
/*  59:    */   {
/*  60: 95 */     this.sucursal = sucursal;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Integer getCliente()
/*  64:    */   {
/*  65:102 */     return this.cliente;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setCliente(Integer cliente)
/*  69:    */   {
/*  70:110 */     this.cliente = cliente;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Integer getSubcliente()
/*  74:    */   {
/*  75:117 */     return this.subcliente;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setSubcliente(Integer subcliente)
/*  79:    */   {
/*  80:125 */     this.subcliente = subcliente;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getNota()
/*  84:    */   {
/*  85:132 */     return this.nota;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setNota(String nota)
/*  89:    */   {
/*  90:140 */     this.nota = nota;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public List<DetalleDevolucionRequestDto> getListaDetalleDevolucion()
/*  94:    */   {
/*  95:147 */     return this.listaDetalleDevolucion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setListaDetalleDevolucion(List<DetalleDevolucionRequestDto> listaDetalleDevolucion)
/*  99:    */   {
/* 100:155 */     this.listaDetalleDevolucion = listaDetalleDevolucion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Integer getPuntoVenta()
/* 104:    */   {
/* 105:159 */     return this.puntoVenta;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setPuntoVenta(Integer puntoVenta)
/* 109:    */   {
/* 110:163 */     this.puntoVenta = puntoVenta;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getFecha()
/* 114:    */   {
/* 115:167 */     return this.fecha;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setFecha(String fecha)
/* 119:    */   {
/* 120:171 */     this.fecha = fecha;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getCodigoMovil()
/* 124:    */   {
/* 125:175 */     return this.codigoMovil;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setCodigoMovil(String codigoMovil)
/* 129:    */   {
/* 130:179 */     this.codigoMovil = codigoMovil;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Integer getFacturaPadre()
/* 134:    */   {
/* 135:183 */     return this.facturaPadre;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setFacturaPadre(Integer facturaPadre)
/* 139:    */   {
/* 140:187 */     this.facturaPadre = facturaPadre;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String getUrlApp()
/* 144:    */   {
/* 145:191 */     return this.urlApp;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setUrlApp(String urlApp)
/* 149:    */   {
/* 150:195 */     this.urlApp = urlApp;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Integer getMotivoNotaCreditoCliente()
/* 154:    */   {
/* 155:199 */     return this.motivoNotaCreditoCliente;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setMotivoNotaCreditoCliente(Integer motivoNotaCreditoCliente)
/* 159:    */   {
/* 160:203 */     this.motivoNotaCreditoCliente = motivoNotaCreditoCliente;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public BigDecimal getTotal()
/* 164:    */   {
/* 165:207 */     return this.total;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setTotal(BigDecimal total)
/* 169:    */   {
/* 170:211 */     this.total = total;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Integer getIdDispositivoSincronizacion()
/* 174:    */   {
/* 175:215 */     return this.idDispositivoSincronizacion;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 179:    */   {
/* 180:219 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public String getNumeroAs2()
/* 184:    */   {
/* 185:223 */     return this.numeroAs2;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setNumeroAs2(String numeroAs2)
/* 189:    */   {
/* 190:227 */     this.numeroAs2 = numeroAs2;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Integer getIdAs2()
/* 194:    */   {
/* 195:231 */     return this.idAs2;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setIdAs2(Integer idAs2)
/* 199:    */   {
/* 200:235 */     this.idAs2 = idAs2;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public Boolean getIndicadorDevolucion()
/* 204:    */   {
/* 205:239 */     return this.indicadorDevolucion;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setIndicadorDevolucion(Boolean indicadorDevolucion)
/* 209:    */   {
/* 210:243 */     this.indicadorDevolucion = indicadorDevolucion;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public String getDescripcion()
/* 214:    */   {
/* 215:247 */     return this.descripcion;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setDescripcion(String descripcion)
/* 219:    */   {
/* 220:251 */     this.descripcion = descripcion;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public String getReferencia8()
/* 224:    */   {
/* 225:255 */     return this.referencia8;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setReferencia8(String referencia8)
/* 229:    */   {
/* 230:259 */     this.referencia8 = referencia8;
/* 231:    */   }
/* 232:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.CrearDevolucionRequestDto
 * JD-Core Version:    0.7.0.1
 */