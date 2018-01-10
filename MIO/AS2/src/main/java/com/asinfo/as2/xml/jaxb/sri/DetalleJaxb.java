/*   1:    */ package com.asinfo.as2.xml.jaxb.sri;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.xml.bind.annotation.XmlElement;
/*   5:    */ import javax.xml.bind.annotation.XmlRootElement;
/*   6:    */ import javax.xml.bind.annotation.XmlType;
/*   7:    */ 
/*   8:    */ @XmlRootElement(name="detalle")
/*   9:    */ @XmlType(propOrder={"codigoPrincipal", "codigoAuxiliar", "codigoInterno", "descripcion", "cantidad", "precioUnitario", "precioSinSubsidio", "descuento", "precioTotalSinImpuesto", "detallesAdicionales", "impuestos"})
/*  10:    */ public class DetalleJaxb
/*  11:    */ {
/*  12:    */   private String codigoPrincipal;
/*  13:    */   private String codigoAuxiliar;
/*  14:    */   private String codigoInterno;
/*  15:    */   private String descripcion;
/*  16:    */   private BigDecimal cantidad;
/*  17:    */   private BigDecimal precioUnitario;
/*  18:    */   private BigDecimal precioSinSubsidio;
/*  19:    */   private BigDecimal descuento;
/*  20:    */   private BigDecimal precioTotalSinImpuesto;
/*  21:    */   private ImpuestosJaxb impuestos;
/*  22:    */   private DetalleAdicionalJaxb detallesAdicionales;
/*  23:    */   
/*  24:    */   @XmlElement(name="codigoPrincipal")
/*  25:    */   public String getCodigoPrincipal()
/*  26:    */   {
/*  27: 42 */     return this.codigoPrincipal;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setCodigoPrincipal(String codigoPrincipal)
/*  31:    */   {
/*  32: 46 */     this.codigoPrincipal = codigoPrincipal;
/*  33:    */   }
/*  34:    */   
/*  35:    */   @XmlElement(name="descripcion")
/*  36:    */   public String getDescripcion()
/*  37:    */   {
/*  38: 51 */     return this.descripcion;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setDescripcion(String descripcion)
/*  42:    */   {
/*  43: 55 */     this.descripcion = descripcion;
/*  44:    */   }
/*  45:    */   
/*  46:    */   @XmlElement(name="cantidad")
/*  47:    */   public BigDecimal getCantidad()
/*  48:    */   {
/*  49: 60 */     return this.cantidad;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setCantidad(BigDecimal cantidad)
/*  53:    */   {
/*  54: 64 */     this.cantidad = cantidad;
/*  55:    */   }
/*  56:    */   
/*  57:    */   @XmlElement(name="precioUnitario")
/*  58:    */   public BigDecimal getPrecioUnitario()
/*  59:    */   {
/*  60: 69 */     return this.precioUnitario;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setPrecioUnitario(BigDecimal precioUnitario)
/*  64:    */   {
/*  65: 73 */     this.precioUnitario = precioUnitario;
/*  66:    */   }
/*  67:    */   
/*  68:    */   @XmlElement(name="precioSinSubsidio")
/*  69:    */   public BigDecimal getPrecioSinSubsidio()
/*  70:    */   {
/*  71: 78 */     return this.precioSinSubsidio;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setPrecioSinSubsidio(BigDecimal precioSinSubsidio)
/*  75:    */   {
/*  76: 82 */     this.precioSinSubsidio = precioSinSubsidio;
/*  77:    */   }
/*  78:    */   
/*  79:    */   @XmlElement(name="descuento")
/*  80:    */   public BigDecimal getDescuento()
/*  81:    */   {
/*  82: 87 */     return this.descuento;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setDescuento(BigDecimal descuento)
/*  86:    */   {
/*  87: 91 */     this.descuento = descuento;
/*  88:    */   }
/*  89:    */   
/*  90:    */   @XmlElement(name="precioTotalSinImpuesto")
/*  91:    */   public BigDecimal getPrecioTotalSinImpuesto()
/*  92:    */   {
/*  93: 96 */     return this.precioTotalSinImpuesto;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setPrecioTotalSinImpuesto(BigDecimal precioTotalSinImpuesto)
/*  97:    */   {
/*  98:100 */     this.precioTotalSinImpuesto = precioTotalSinImpuesto;
/*  99:    */   }
/* 100:    */   
/* 101:    */   @XmlElement(name="impuestos")
/* 102:    */   public ImpuestosJaxb getImpuestos()
/* 103:    */   {
/* 104:105 */     return this.impuestos;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setImpuestos(ImpuestosJaxb impuestos)
/* 108:    */   {
/* 109:109 */     this.impuestos = impuestos;
/* 110:    */   }
/* 111:    */   
/* 112:    */   @XmlElement(name="codigoInterno")
/* 113:    */   public String getCodigoInterno()
/* 114:    */   {
/* 115:114 */     return this.codigoInterno;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setCodigoInterno(String codigoInterno)
/* 119:    */   {
/* 120:118 */     this.codigoInterno = codigoInterno;
/* 121:    */   }
/* 122:    */   
/* 123:    */   @XmlElement(name="codigoAuxiliar")
/* 124:    */   public String getCodigoAuxiliar()
/* 125:    */   {
/* 126:123 */     return this.codigoAuxiliar;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setCodigoAuxiliar(String codigoAuxiliar)
/* 130:    */   {
/* 131:127 */     this.codigoAuxiliar = codigoAuxiliar;
/* 132:    */   }
/* 133:    */   
/* 134:    */   @XmlElement(name="detallesAdicionales")
/* 135:    */   public DetalleAdicionalJaxb getDetallesAdicionales()
/* 136:    */   {
/* 137:133 */     return this.detallesAdicionales;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setDetallesAdicionales(DetalleAdicionalJaxb detallesAdicionales)
/* 141:    */   {
/* 142:137 */     this.detallesAdicionales = detallesAdicionales;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.DetalleJaxb
 * JD-Core Version:    0.7.0.1
 */