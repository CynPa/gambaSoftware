/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.NotNull;
/*  11:    */ import javax.validation.constraints.Size;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="codigo_forma_pagoSRI", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  15:    */ public class CodigoFormaPagoSRI
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="codigo_forma_pagoSRI", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="codigo_forma_pagoSRI")
/*  22:    */   @Column(name="id_codigo_forma_pagoSRI")
/*  23:    */   private int idCodigoFormaPagoSRI;
/*  24:    */   @Column(name="id_organizacion", nullable=false)
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="activo", nullable=false)
/*  27:    */   private boolean activo;
/*  28:    */   @Column(name="predeterminado", nullable=false)
/*  29:    */   private boolean predeterminado;
/*  30:    */   @Column(name="codigo", nullable=false, length=3)
/*  31:    */   @NotNull
/*  32:    */   @Size(min=2, max=10)
/*  33:    */   private String codigo;
/*  34:    */   @Column(name="nombre", nullable=false, length=50)
/*  35:    */   @NotNull
/*  36:    */   @Size(max=50)
/*  37:    */   private String nombre;
/*  38:    */   @Column(name="descripcion", length=200)
/*  39:    */   @Size(max=200)
/*  40:    */   private String descripcion;
/*  41:    */   
/*  42:    */   public int getId()
/*  43:    */   {
/*  44: 67 */     return this.idCodigoFormaPagoSRI;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int getIdCodigoFormaPagoSRI()
/*  48:    */   {
/*  49: 71 */     return this.idCodigoFormaPagoSRI;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setIdCodigoFormaPagoSRI(int idCodigoFormaPagoSRI)
/*  53:    */   {
/*  54: 75 */     this.idCodigoFormaPagoSRI = idCodigoFormaPagoSRI;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getIdOrganizacion()
/*  58:    */   {
/*  59: 79 */     return this.idOrganizacion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setIdOrganizacion(int idOrganizacion)
/*  63:    */   {
/*  64: 83 */     this.idOrganizacion = idOrganizacion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean isActivo()
/*  68:    */   {
/*  69: 87 */     return this.activo;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setActivo(boolean activo)
/*  73:    */   {
/*  74: 91 */     this.activo = activo;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean isPredeterminado()
/*  78:    */   {
/*  79: 95 */     return this.predeterminado;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setPredeterminado(boolean predeterminado)
/*  83:    */   {
/*  84: 99 */     this.predeterminado = predeterminado;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getCodigo()
/*  88:    */   {
/*  89:103 */     return this.codigo;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setCodigo(String codigo)
/*  93:    */   {
/*  94:107 */     this.codigo = codigo;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getNombre()
/*  98:    */   {
/*  99:111 */     return this.nombre;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setNombre(String nombre)
/* 103:    */   {
/* 104:115 */     this.nombre = nombre;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getDescripcion()
/* 108:    */   {
/* 109:119 */     return this.descripcion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setDescripcion(String descripcion)
/* 113:    */   {
/* 114:123 */     this.descripcion = descripcion;
/* 115:    */   }
/* 116:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CodigoFormaPagoSRI
 * JD-Core Version:    0.7.0.1
 */