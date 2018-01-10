/*   1:    */ package com.asinfo.as2.xml.jaxb.sri;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.annotation.XmlElement;
/*   4:    */ import javax.xml.bind.annotation.XmlRootElement;
/*   5:    */ import javax.xml.bind.annotation.XmlType;
/*   6:    */ 
/*   7:    */ @XmlRootElement(name="destinatario")
/*   8:    */ @XmlType(propOrder={"identificacionDestinatario", "razonSocialDestinatario", "dirDestinatario", "motivoTraslado", "docAduaneroUnico", "codEstabDestino", "ruta", "codDocSustento", "numDocSustento", "numAutDocSustento", "fechaEmisionDocSustento", "detalles"})
/*   9:    */ public class DestinatarioJaxb
/*  10:    */ {
/*  11:    */   private String identificacionDestinatario;
/*  12:    */   private String razonSocialDestinatario;
/*  13:    */   private String dirDestinatario;
/*  14:    */   private String motivoTraslado;
/*  15:    */   private String docAduaneroUnico;
/*  16:    */   private String codEstabDestino;
/*  17:    */   private String ruta;
/*  18:    */   private String codDocSustento;
/*  19:    */   private String numDocSustento;
/*  20:    */   private String numAutDocSustento;
/*  21:    */   private String fechaEmisionDocSustento;
/*  22:    */   private DetallesJaxb detalles;
/*  23:    */   
/*  24:    */   @XmlElement(name="identificacionDestinatario")
/*  25:    */   public String getIdentificacionDestinatario()
/*  26:    */   {
/*  27: 42 */     return this.identificacionDestinatario;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setIdentificacionDestinatario(String identificacionDestinatario)
/*  31:    */   {
/*  32: 46 */     this.identificacionDestinatario = identificacionDestinatario;
/*  33:    */   }
/*  34:    */   
/*  35:    */   @XmlElement(name="razonSocialDestinatario")
/*  36:    */   public String getRazonSocialDestinatario()
/*  37:    */   {
/*  38: 51 */     return this.razonSocialDestinatario;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setRazonSocialDestinatario(String razonSocialDestinatario)
/*  42:    */   {
/*  43: 55 */     this.razonSocialDestinatario = razonSocialDestinatario;
/*  44:    */   }
/*  45:    */   
/*  46:    */   @XmlElement(name="dirDestinatario")
/*  47:    */   public String getDirDestinatario()
/*  48:    */   {
/*  49: 60 */     return this.dirDestinatario;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setDirDestinatario(String dirDestinatario)
/*  53:    */   {
/*  54: 64 */     this.dirDestinatario = dirDestinatario;
/*  55:    */   }
/*  56:    */   
/*  57:    */   @XmlElement(name="motivoTraslado")
/*  58:    */   public String getMotivoTraslado()
/*  59:    */   {
/*  60: 69 */     return this.motivoTraslado;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setMotivoTraslado(String motivoTraslado)
/*  64:    */   {
/*  65: 73 */     this.motivoTraslado = motivoTraslado;
/*  66:    */   }
/*  67:    */   
/*  68:    */   @XmlElement(name="docAduaneroUnico")
/*  69:    */   public String getDocAduaneroUnico()
/*  70:    */   {
/*  71: 78 */     return this.docAduaneroUnico;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setDocAduaneroUnico(String docAduaneroUnico)
/*  75:    */   {
/*  76: 82 */     this.docAduaneroUnico = docAduaneroUnico;
/*  77:    */   }
/*  78:    */   
/*  79:    */   @XmlElement(name="codEstabDestino")
/*  80:    */   public String getCodEstabDestino()
/*  81:    */   {
/*  82: 87 */     return this.codEstabDestino;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setCodEstabDestino(String codEstabDestino)
/*  86:    */   {
/*  87: 91 */     this.codEstabDestino = codEstabDestino;
/*  88:    */   }
/*  89:    */   
/*  90:    */   @XmlElement(name="ruta")
/*  91:    */   public String getRuta()
/*  92:    */   {
/*  93: 96 */     return this.ruta;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setRuta(String ruta)
/*  97:    */   {
/*  98:100 */     this.ruta = ruta;
/*  99:    */   }
/* 100:    */   
/* 101:    */   @XmlElement(name="codDocSustento")
/* 102:    */   public String getCodDocSustento()
/* 103:    */   {
/* 104:105 */     return this.codDocSustento;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setCodDocSustento(String codDocSustento)
/* 108:    */   {
/* 109:109 */     this.codDocSustento = codDocSustento;
/* 110:    */   }
/* 111:    */   
/* 112:    */   @XmlElement(name="numDocSustento")
/* 113:    */   public String getNumDocSustento()
/* 114:    */   {
/* 115:114 */     return this.numDocSustento;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setNumDocSustento(String numDocSustento)
/* 119:    */   {
/* 120:118 */     this.numDocSustento = numDocSustento;
/* 121:    */   }
/* 122:    */   
/* 123:    */   @XmlElement(name="numAutDocSustento")
/* 124:    */   public String getNumAutDocSustento()
/* 125:    */   {
/* 126:123 */     return this.numAutDocSustento;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setNumAutDocSustento(String numAutDocSustento)
/* 130:    */   {
/* 131:127 */     this.numAutDocSustento = numAutDocSustento;
/* 132:    */   }
/* 133:    */   
/* 134:    */   @XmlElement(name="fechaEmisionDocSustento")
/* 135:    */   public String getFechaEmisionDocSustento()
/* 136:    */   {
/* 137:132 */     return this.fechaEmisionDocSustento;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setFechaEmisionDocSustento(String fechaEmisionDocSustento)
/* 141:    */   {
/* 142:136 */     this.fechaEmisionDocSustento = fechaEmisionDocSustento;
/* 143:    */   }
/* 144:    */   
/* 145:    */   @XmlElement(name="detalles")
/* 146:    */   public DetallesJaxb getDetalles()
/* 147:    */   {
/* 148:141 */     return this.detalles;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setDetalles(DetallesJaxb detalles)
/* 152:    */   {
/* 153:145 */     this.detalles = detalles;
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.DestinatarioJaxb
 * JD-Core Version:    0.7.0.1
 */