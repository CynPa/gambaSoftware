/*   1:    */ package com.asinfo.as2.finaciero.SRI.reportes;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ 
/*   7:    */ public class Campo103
/*   8:    */ {
/*   9:    */   private String codigo;
/*  10: 26 */   private BigDecimal valor = BigDecimal.ZERO;
/*  11:    */   private Campo103 campoAsociado;
/*  12:    */   private String strValor;
/*  13:    */   private boolean campoTexto;
/*  14: 31 */   private List<Campo103> listaSumandos = new ArrayList();
/*  15:    */   
/*  16:    */   public Campo103(String codigo)
/*  17:    */   {
/*  18: 36 */     this.codigo = codigo;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Campo103(String codigo, Campo103 campoAsociado)
/*  22:    */   {
/*  23: 41 */     this.codigo = codigo;
/*  24: 42 */     this.campoAsociado = campoAsociado;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String getCodigo()
/*  28:    */   {
/*  29: 50 */     return this.codigo;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setCodigo(String codigo)
/*  33:    */   {
/*  34: 58 */     this.codigo = codigo;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public BigDecimal getValor()
/*  38:    */   {
/*  39: 66 */     return this.valor;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setValor(BigDecimal valor)
/*  43:    */   {
/*  44: 74 */     this.valor = valor;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Campo103 getCampoAsociado()
/*  48:    */   {
/*  49: 82 */     return this.campoAsociado;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setCampoAsociado(Campo103 campoAsociado)
/*  53:    */   {
/*  54: 90 */     this.campoAsociado = campoAsociado;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<Campo103> getListaSumandos()
/*  58:    */   {
/*  59: 98 */     return this.listaSumandos;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setListaSumandos(List<Campo103> listaSumandos)
/*  63:    */   {
/*  64:106 */     this.listaSumandos = listaSumandos;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getStrValor()
/*  68:    */   {
/*  69:114 */     return this.strValor;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setStrValor(String strValor)
/*  73:    */   {
/*  74:122 */     this.strValor = strValor;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean isCampoTexto()
/*  78:    */   {
/*  79:130 */     return this.campoTexto;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setCampoTexto(boolean campoTexto)
/*  83:    */   {
/*  84:138 */     this.campoTexto = campoTexto;
/*  85:    */   }
/*  86:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.reportes.Campo103
 * JD-Core Version:    0.7.0.1
 */