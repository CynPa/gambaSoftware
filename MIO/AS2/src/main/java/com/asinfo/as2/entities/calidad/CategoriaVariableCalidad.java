/*   1:    */ package com.asinfo.as2.entities.calidad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ import javax.validation.constraints.NotNull;
/*  13:    */ import javax.validation.constraints.Size;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="categoria_variable_calidad", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  17:    */ public class CategoriaVariableCalidad
/*  18:    */   extends EntidadBase
/*  19:    */   implements Serializable
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="categoria_variable_calidad", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="categoria_variable_calidad")
/*  25:    */   @Column(name="id_categoria_variable_calidad")
/*  26:    */   private int idCategoriaVariableCalidad;
/*  27:    */   @Column(name="id_organizacion")
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal")
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="codigo", nullable=false, length=20)
/*  32:    */   @NotNull
/*  33:    */   @Size(min=1, max=20)
/*  34:    */   private String codigo;
/*  35:    */   @Column(name="nombre", nullable=false, length=100)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=2, max=100)
/*  38:    */   private String nombre;
/*  39:    */   @Column(name="predeterminado", nullable=false)
/*  40:    */   private boolean predeterminado;
/*  41:    */   @Column(name="activo", nullable=false)
/*  42:    */   private boolean activo;
/*  43:    */   
/*  44:    */   public int getId()
/*  45:    */   {
/*  46: 72 */     return this.idCategoriaVariableCalidad;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getIdCategoriaVariableCalidad()
/*  50:    */   {
/*  51: 79 */     return this.idCategoriaVariableCalidad;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIdCategoriaVariableCalidad(int idCategoriaVariableCalidad)
/*  55:    */   {
/*  56: 87 */     this.idCategoriaVariableCalidad = idCategoriaVariableCalidad;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getIdOrganizacion()
/*  60:    */   {
/*  61: 94 */     return this.idOrganizacion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdOrganizacion(int idOrganizacion)
/*  65:    */   {
/*  66:102 */     this.idOrganizacion = idOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getIdSucursal()
/*  70:    */   {
/*  71:109 */     return this.idSucursal;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdSucursal(int idSucursal)
/*  75:    */   {
/*  76:117 */     this.idSucursal = idSucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getCodigo()
/*  80:    */   {
/*  81:124 */     return this.codigo;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setCodigo(String codigo)
/*  85:    */   {
/*  86:132 */     this.codigo = codigo;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getNombre()
/*  90:    */   {
/*  91:139 */     return this.nombre;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setNombre(String nombre)
/*  95:    */   {
/*  96:147 */     this.nombre = nombre;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isPredeterminado()
/* 100:    */   {
/* 101:154 */     return this.predeterminado;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setPredeterminado(boolean predeterminado)
/* 105:    */   {
/* 106:162 */     this.predeterminado = predeterminado;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isActivo()
/* 110:    */   {
/* 111:169 */     return this.activo;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setActivo(boolean activo)
/* 115:    */   {
/* 116:177 */     this.activo = activo;
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.calidad.CategoriaVariableCalidad
 * JD-Core Version:    0.7.0.1
 */