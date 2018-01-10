/*   1:    */ package com.asinfo.as2.rs.inventario.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.List;
/*   6:    */ 
/*   7:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   8:    */ public class CrearPedidoRequestDto
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private Integer usuario;
/*  12:    */   private Integer organizacion;
/*  13:    */   private Integer sucursal;
/*  14:    */   private Integer bodega;
/*  15:    */   private String fecha;
/*  16:    */   private Integer cliente;
/*  17:    */   private Integer subcliente;
/*  18:    */   private String nota;
/*  19:    */   private String codigoMovil;
/*  20:    */   private String urlApp;
/*  21:    */   private String referencia8;
/*  22:    */   private List<DetallePedidoRequestDto> listaDetallePedido;
/*  23:    */   private String fechaDespacho;
/*  24:    */   private Boolean indicadorCualquierFecha;
/*  25:    */   private Integer idCondicionPago;
/*  26:    */   private Integer numeroCuotas;
/*  27:    */   
/*  28:    */   public Integer getUsuario()
/*  29:    */   {
/*  30: 46 */     return this.usuario;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setUsuario(Integer usuario)
/*  34:    */   {
/*  35: 50 */     this.usuario = usuario;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Integer getOrganizacion()
/*  39:    */   {
/*  40: 54 */     return this.organizacion;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setOrganizacion(Integer organizacion)
/*  44:    */   {
/*  45: 58 */     this.organizacion = organizacion;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Integer getSucursal()
/*  49:    */   {
/*  50: 62 */     return this.sucursal;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setSucursal(Integer sucursal)
/*  54:    */   {
/*  55: 66 */     this.sucursal = sucursal;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Integer getBodega()
/*  59:    */   {
/*  60: 70 */     return this.bodega;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setBodega(Integer bodega)
/*  64:    */   {
/*  65: 74 */     this.bodega = bodega;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getFecha()
/*  69:    */   {
/*  70: 78 */     return this.fecha;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setFecha(String fecha)
/*  74:    */   {
/*  75: 82 */     this.fecha = fecha;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Integer getCliente()
/*  79:    */   {
/*  80: 86 */     return this.cliente;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setCliente(Integer cliente)
/*  84:    */   {
/*  85: 90 */     this.cliente = cliente;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Integer getSubcliente()
/*  89:    */   {
/*  90: 94 */     return this.subcliente;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setSubcliente(Integer subcliente)
/*  94:    */   {
/*  95: 98 */     this.subcliente = subcliente;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getNota()
/*  99:    */   {
/* 100:102 */     return this.nota;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setNota(String nota)
/* 104:    */   {
/* 105:106 */     this.nota = nota;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public List<DetallePedidoRequestDto> getListaDetallePedido()
/* 109:    */   {
/* 110:110 */     return this.listaDetallePedido;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setListaDetallePedido(List<DetallePedidoRequestDto> listaDetallePedido)
/* 114:    */   {
/* 115:114 */     this.listaDetallePedido = listaDetallePedido;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getCodigoMovil()
/* 119:    */   {
/* 120:118 */     return this.codigoMovil;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setCodigoMovil(String codigoMovil)
/* 124:    */   {
/* 125:122 */     this.codigoMovil = codigoMovil;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getUrlApp()
/* 129:    */   {
/* 130:126 */     return this.urlApp;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setUrlApp(String urlApp)
/* 134:    */   {
/* 135:130 */     this.urlApp = urlApp;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String getFechaDespacho()
/* 139:    */   {
/* 140:134 */     return this.fechaDespacho;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setFechaDespacho(String fechaDespacho)
/* 144:    */   {
/* 145:138 */     this.fechaDespacho = fechaDespacho;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Boolean getIndicadorCualquierFecha()
/* 149:    */   {
/* 150:142 */     return this.indicadorCualquierFecha;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setIndicadorCualquierFecha(Boolean indicadorCualquierFecha)
/* 154:    */   {
/* 155:146 */     this.indicadorCualquierFecha = indicadorCualquierFecha;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Integer getIdCondicionPago()
/* 159:    */   {
/* 160:150 */     return this.idCondicionPago;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setIdCondicionPago(Integer idCondicionPago)
/* 164:    */   {
/* 165:154 */     this.idCondicionPago = idCondicionPago;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Integer getNumeroCuotas()
/* 169:    */   {
/* 170:158 */     return this.numeroCuotas;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setNumeroCuotas(Integer numeroCuotas)
/* 174:    */   {
/* 175:162 */     this.numeroCuotas = numeroCuotas;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String getReferencia8()
/* 179:    */   {
/* 180:166 */     return this.referencia8;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setReferencia8(String referencia8)
/* 184:    */   {
/* 185:170 */     this.referencia8 = referencia8;
/* 186:    */   }
/* 187:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.CrearPedidoRequestDto
 * JD-Core Version:    0.7.0.1
 */